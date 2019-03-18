import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Driver extends Application {
    GridPane bg;
    BorderPane bp;
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoaderAndSaver load = new LoaderAndSaver();
        load.load();
        Tama tam = load.getTama();
        tam.feed();
        System.out.println(tam);

        primaryStage.setTitle("Tamagatchi");
        bp = new BorderPane();
        Scene scene = new Scene(bp,200, 250);
        primaryStage.setScene(scene);

        bg = new GridPane();
        createBackground();
        bp.setCenter(bg);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Closing");
            load.save(tam);
        });
    }

    public void createBackground(){
        Image border = new Image(Driver.class.getResourceAsStream("images/Border.png"));
        Image health = new Image(Driver.class.getResourceAsStream("images/HealthBar.png"));
        bg.getChildren().add(new ImageView(border));
        bg.getChildren().add()

    }
}
