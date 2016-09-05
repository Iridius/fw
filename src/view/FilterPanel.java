package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import model.UserForm;

/* Панель фильтров */
public class FilterPanel extends StackPane {
    public FilterPanel(UserForm form){
        this.setAlignment(Pos.BASELINE_LEFT);

        Label lab = new Label();
        lab.setText("Панель фильтров");
        this.getChildren().add(lab);
    }
}
