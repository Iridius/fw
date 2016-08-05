package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.UserForm;
import model.UserFormColumn;

import java.util.ArrayList;
import java.util.Collection;

public class TableDisplay {
    public static Tab create(UserForm form) {
        /* Вкладка с табличным представлением */
        Tab tab = new Tab();
        tab.setText(form.getItem());
        tab.setId(form.getViewName());
        tab.setOnClosed(event -> {
            if (tab.getTabPane() != null) {
                tab.getTabPane().setVisible(false);
            }
        });

        /* Сетка таблицы */
        TableView table = new TableView();
        table.getColumns().addAll(getTableColumns(form));
        tab.setContent(table);

        return tab;
    }

    private static Collection<TableColumn> getTableColumns(UserForm form) {
        Collection<TableColumn> columns = new ArrayList<>();

        for(UserFormColumn form_column: form.getColumns()){
            TableColumn column = new TableColumn(form_column.get("caption"));
            column.setVisible(Boolean.valueOf(getValue(form_column.get("visible"), "true")));
            columns.add(column);

        }

        return columns;
    }

    private static String getValue(String value, String default_value) {
        return value != null? value.toLowerCase(): default_value;
    }
}
