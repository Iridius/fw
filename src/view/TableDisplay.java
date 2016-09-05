package view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.UserForm;
import model.UserFormColumn;
import java.util.ArrayList;
import java.util.Collection;

public class TableDisplay {
    private TableView _table;

    public Tab create(UserForm form) {
        /* Сетка таблицы */
        _table = new TableView();
        _table.setEditable(true);
        _table.getColumns().addAll(getTableColumns(form));

        /* Заголовок, фильтры, элементы управления */
        VBox box = new VBox();
        box.setPadding(new Insets(5, 5, 5, 5));
        box.setSpacing(5);
        box.getChildren().addAll(new FilterPanel(form), new ControlPanel(_table, form));

        /* Рабочая область формы */
        BorderPane pane = new BorderPane();
        pane.setTop(box);
        pane.setCenter(_table);

        /* Вкладка с табличным представлением */
        Tab tab = new Tab();
        tab.setText(form.getItem());
        tab.setId(form.getViewName());
        tab.setOnClosed(event -> {
            if (tab.getTabPane() != null) {
                tab.getTabPane().setVisible(false);
            }
        });
        tab.setContent(pane);

        return tab;
    }

    private static Collection<TableColumn> getTableColumns(UserForm form) {
        Collection<TableColumn> columns = new ArrayList<>();

        for(UserFormColumn form_column: form.getColumns()){
            Label label = new Label(form_column.getCaption());
            label.setTooltip(new Tooltip(form_column.getTooltip()));

            TableColumn<UserFormColumn, String> column = new TableColumn<>();
            column.setCellValueFactory(new PropertyValueFactory(form_column.get("key")));
            column.setId(form_column.get("key"));
            column.setVisible(form_column.getVisible());
            column.setGraphic(label);
            columns.add(column);
        }

        return columns;
    }

    private static String getValue(String value, String default_value) {
        return value != null? value.toLowerCase(): default_value;
    }
}
