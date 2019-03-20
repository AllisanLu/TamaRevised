import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Driver extends Application {
    GridPane bg;
    BorderPane root;
    private Tama currentTama;

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoaderAndSaver load = new LoaderAndSaver();
        load.load();
        currentTama = load.getTama();
        currentTama.feed();
        System.out.println(currentTama);

        primaryStage.setTitle("Tama");
        root = new BorderPane();
        root.setId("root");
        Scene scene = new Scene(root,200, 250);
        scene.getStylesheets().add("background.css");
        primaryStage.setScene(scene);

        bg = new GridPane();
        root.setCenter(bg);
        createButtons();
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Closing");
            load.save(currentTama);
        });
    }

    private void createIcons(){
        Image health = new Image("images/HealthBar.png");
        bg.add(new ImageView(health),3, 0);

   }

   private void createButtons() {
        HBox buttonContainer = new HBox(20);
        buttonContainer.setAlignment(Pos.CENTER);

        String pathToButtons = "images/buttons/";

       Image feedImage = new Image(pathToButtons + "FeedButton.png");
       Button feed = new Button();
       feed.setGraphic(new ImageView(feedImage));
       feed.setOnMouseClicked(e -> currentTama.feed());
       buttonContainer.getChildren().add(feed);

       Image cleanImage = new Image(pathToButtons + "CleanButton.png");
       Button clean = new Button();
       clean.setGraphic(new ImageView(cleanImage));
       clean.setMaxSize(cleanImage.getWidth(), cleanImage.getHeight());
       clean.setOnMouseClicked(e -> currentTama.cleanPoop());
       buttonContainer.getChildren().add(clean);

       Image resetImage = new Image(pathToButtons + "ResetButton.png");
       ImageView resetImageView = new ImageView(resetImage);
       resetImageView.setFitWidth(resetImage.getWidth());
       resetImageView.setFitHeight(resetImage.getHeight());
       Button reset = new Button();
       reset.setGraphic(resetImageView);
       reset.setMaxSize(resetImage.getWidth(), resetImage.getHeight());
       reset.setOnMouseClicked(e -> currentTama.reset());
       buttonContainer.getChildren().add(reset);

       root.setBottom(buttonContainer);
   }
}
