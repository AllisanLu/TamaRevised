import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Driver extends Application {
    GridPane bg;
    BorderPane root;
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoaderAndSaver load = new LoaderAndSaver();
        load.load();
        Tama tam = load.getTama();
        tam.feed();
        System.out.println(tam);

        primaryStage.setTitle("Tama");
        root = new BorderPane();
        root.setId("root");
        Scene scene = new Scene(root,200, 250);
        scene.getStylesheets().add("background.css");
        primaryStage.setScene(scene);

        bg = new GridPane();
        root.setCenter(bg);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Closing");
            load.save(tam);
        });
    }

    public void createIcons(){
        Image health = new Image(Driver.class.getResourceAsStream("images/HealthBar.png"));
        bg.add(new ImageView(health),3, 0);

   }
}
