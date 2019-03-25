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
    private Image tamaDisplay;

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoaderAndSaver load = new LoaderAndSaver();

        currentTama = load.load();
        System.out.println(currentTama);
        tamaDisplay = currentTama.getLooks();

        primaryStage.setTitle("Tama");
        root = new BorderPane();
        root.setId("root");
        Scene scene = new Scene(root,200, 250);
        scene.getStylesheets().add("background.css");
        primaryStage.setScene(scene);

        bg = new GridPane();
        root.setCenter(bg);
        createButtons();
        root.setCenter(new ImageView(tamaDisplay));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Closing");
            load.save(currentTama);
        });
        hunger();
    }

    private void createIcons(){
        Image health = new Image("images/HealthBar.png");
        bg.add(new ImageView(health),3, 0);

   }

   private void createButtons() {
        HBox buttonContainer = new HBox(5);
        buttonContainer.setAlignment(Pos.CENTER);

        String pathToButtons = "images/buttons/";
        String[] buttonPaths = {pathToButtons + "FeedButton.png", pathToButtons + "CleanButton.png", pathToButtons + "ResetButton.png"};

        Button[] buttons = new Button[buttonPaths.length];

        for(int i = 0; i < buttons.length; i++) {
            Button button = new Button();
            Image image = new Image(buttonPaths[i]);
            button.setMinSize(image.getWidth(), image.getHeight());

            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);
            button.setBackground(background);
            buttonContainer.getChildren().add(button);
            buttons[i] = button;
        }

        buttons[0].setOnMouseClicked(e -> currentTama.feed());
        buttons[1].setOnMouseClicked(e -> currentTama.cleanPoop());
        buttons[2].setOnMouseClicked(e -> currentTama.cleanPoop());

        root.setBottom(buttonContainer);
   }

    private void hunger(){
        new Thread( () -> {
            double begin = System.currentTimeMillis();
            while(true){
                double current = System.currentTimeMillis();
                if((current - begin) >= 60000 && currentTama.getFood() > 0) {
                    currentTama.setFood(currentTama.getFood() - 1);
                    if(currentTama.getFood() == 0)
                        System.out.println("IM HUNGRYYY");
                    begin = System.currentTimeMillis();
                }
            }
        }
        ).start();
    }

}
