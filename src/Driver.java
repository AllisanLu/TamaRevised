import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Driver extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoaderAndSaver load = new LoaderAndSaver();
        load.load();
        Tama tam = load.getTama();
        tam.feed();
        System.out.println(tam);

        primaryStage.setTitle("Tamagatchi");
        primaryStage.setScene(new Scene(new BorderPane(), 300, 300 ));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Closing");
            load.save(tam);
        });
    }
}
