package pizzapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pizzapp.model.Pizzaria;

import java.io.IOException;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/principal.fxml"));


        Scene scene = new Scene(loader.load());
        stage.setScene(scene);

        stage.show();



    }
}
