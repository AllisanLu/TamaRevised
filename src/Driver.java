import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Driver extends Application {
    GridPane bg;
    BorderPane root;
    private Tamas tamas;
    private ImageView tamaDis;
    private Thread hunger;
    private ProgressBar progress;

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoaderAndSaver load = new LoaderAndSaver();

        tamas = load.load();
        VBox tamaDisplay = new VBox();
        HBox poops = new HBox();
//        tamaDisplay.setPrefSize(, 90);
        tamaDis = new ImageView(tamas.getCurrentTama().getLooks());
        tamaDisplay.getChildren().addAll(tamaDis, poops);


        progress = new ProgressBar(tamas.getCurrentTama().getPercentHealth());

        primaryStage.setTitle("Tama");
        root = new BorderPane();
        root.setId("root");
        Scene scene = new Scene(root,200, 250);
        scene.getStylesheets().add("background.css");

        primaryStage.setScene(scene);

        bg = new GridPane();
        root.setTop(progress);
        root.setCenter(bg);
        root.setCenter(tamaDisplay);
        createButtons();
        createMenu();
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            load.save(tamas);
            hunger.stop();
        });
        hunger();
    }

    private void createMenu(){
        ContextMenu menu = new ContextMenu();
        MenuItem jerry = new MenuItem("Jerry");
        jerry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tamas.switchTama("images/jerry/");
                updateTamaDisplay(tamas.getCurrentTama().getLooks());
            }
        });

        MenuItem terry = new MenuItem("Terry");
        terry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tamas.switchTama("images/terry/");
                updateTamaDisplay(tamas.getCurrentTama().getLooks());
            }
        });

        menu.getItems().addAll(jerry, terry);
        root.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event){
                menu.show(root, event.getScreenX(), event.getScreenY());
            }

        });
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

        buttons[0].setOnMouseClicked(e -> tamas.getCurrentTama().feed());
        buttons[1].setOnMouseClicked(e -> tamas.getCurrentTama().cleanPoop());
        buttons[2].setOnMouseClicked(e -> {
            tamas.getCurrentTama().reset();
            updateTamaDisplay(tamas.getCurrentTama().getLooks());
        });

        root.setBottom(buttonContainer);
   }

   private void updateTamaDisplay(Image image) {
        tamaDisplay.setImage(image);
   }

   private void updateProgress(double progress) {
        this.progress.setProgress(progress);
   }
    private void hunger(){
      hunger = new Thread( () -> {
            while(true) {
                tamas.getCurrentTama().update();

                updateTamaDisplay(tamas.getCurrentTama().getLooks());
                updateProgress(tamas.getCurrentTama().getPercentHealth());
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            });
      hunger.start();
    }

}
