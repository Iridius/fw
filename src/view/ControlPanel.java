package view;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import model.DbData;
import model.UserForm;

import java.sql.ResultSet;
import java.sql.SQLException;

/* Панель элементов управления ("сохранить", "обновить" и т.п.) */
public class ControlPanel extends HBox {
    private UserForm _form;
    private TableView _table;

    public ControlPanel(TableView table, UserForm form){
        _form = form;
        _table = table;

        Button btnSave = new Button("Сохранить");
        btnSave.setPrefSize(100, 20);

        Button btnRefresh = new Button("Обновить");
        btnRefresh.setPrefSize(100, 20);
        btnRefresh.setOnAction(btn_Refresh_click);

        this.getChildren().addAll(btnSave, btnRefresh);
    }

    EventHandler<ActionEvent> btn_Refresh_click = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String read_proc = _form.getReadProc();
            if(read_proc == null){
                return;
            }

            ObservableList data = FXCollections.observableArrayList();
            ResultSet rs = null;

            System.out.println(read_proc);
            try {
                rs = DbData.getStatement().executeQuery(read_proc);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
//                while (rs.next()) {
//                    ObservableList<String> row = FXCollections.observableArrayList();
//
//                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                        row.add(rs.getString(i));
//                    }
//
//                    data.add(row);
//
//                }

                for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                    //We are using non property style for making dynamic table
                    final int j = i;
                    TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            if(param.getValue().get(j) != null){
                                return new SimpleStringProperty(param.getValue().get(j).toString());
                            }

                            return new SimpleStringProperty("");
                        }
                    });

                    _table.getColumns().addAll(col);
                }

                /********************************
                 * Data added to ObservableList *
                 ********************************/
                while(rs.next()){
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    data.add(row);

                }

                //FINALLY ADDED TO TableView
                _table.setItems(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //_table.setItems(data);
        }
    };
}
