package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class EntryPoint extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception{
        User current_user = (new Login()).user;

        if(current_user != null){
            new Main(current_user);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
