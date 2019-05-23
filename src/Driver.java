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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Driver extends Application {
    BorderPane root;
    private Tamas tamas;
    private ImageView tamaDis;
    private Text speech;
    private Thread hunger;
    private ProgressBar progress;
    private HBox poops;

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoaderAndSaver load = new LoaderAndSaver();

        tamas = load.load();
        tamaDis = new ImageView(tamas.getCurrentTama().updateLooks());

        poops = new HBox(3);
        for(int i = 0; i < Tama.MAX_POOPS; i++) {
            poops.getChildren().add(new ImageView("images/poo.png"));
        }
        poops.setAlignment(Pos.CENTER);

        speech = new Text("I'm full!");
        speech.setFont(Font.font ("MV Boli", 12));
        updateSpeech();

        VBox tamaDisplay = new VBox(speech, tamaDis);
        tamaDisplay.getChildren().add(poops);
        tamaDisplay.setAlignment(Pos.CENTER);
        updatePoops();

        primaryStage.setTitle("Tama");
        root = new BorderPane();
        root.setId("root");
        Scene scene = new Scene(root,200, 250);
        scene.getStylesheets().add("background.css");
        primaryStage.getIcons().add(new Image("images/Egg.png"));
        primaryStage.setScene(scene);

        progress = new ProgressBar(tamas.getCurrentTama().getPercentHealth());
        progress.setId("progress");
        root.setTop(progress);

        root.setCenter(tamaDisplay);
        createButtons();
        createMenu();
        primaryStage.show();

        hunger();
        primaryStage.setOnCloseRequest(e -> {
            load.save(tamas);
            hunger.stop();
        });
    }

    private void createMenu(){
        ContextMenu menu = new ContextMenu();
        MenuItem jerry = new MenuItem("Jerry");
        jerry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tamas.switchTama("images/jerry/");
                updateTamaDisplay(tamas.getCurrentTama().updateLooks());
            }
        });

        MenuItem terry = new MenuItem("Boo");
        terry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tamas.switchTama("images/boo/");
                updateTamaDisplay(tamas.getCurrentTama().updateLooks());
                tamas.switchTama("images/terry/");
                updateTamaDisplay(tamas.getCurrentTama().updateLooks());
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

        buttons[0].setOnMouseClicked(e -> {
            tamas.getCurrentTama().feed();
            updateSpeech();
            updatePoops();
            updateProgress(tamas.getCurrentTama().getPercentHealth());
        });
        buttons[1].setOnMouseClicked(e -> {
            tamas.getCurrentTama().cleanPoop();
            updatePoops();
        });

        buttons[2].setOnMouseClicked(e -> {
            tamas.getCurrentTama().reset();
            updateTamaDisplay(tamas.getCurrentTama().updateLooks());
            updatePoops();
            updateSpeech();
        });

        root.setBottom(buttonContainer);
   }

    private void hunger(){
        hunger = new Thread( () -> {
            while(true) {
                tamas.getCurrentTama().update();

                updateTamaDisplay(tamas.getCurrentTama().updateLooks());
                updateProgress(tamas.getCurrentTama().getPercentHealth());
                updateSpeech();
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        hunger.start();
    }

    private void updateTamaDisplay(Image image) {
        tamaDis.setImage(image);
   }

   private void updateProgress(double progress) {
        this.progress.setProgress(progress);
   }

    private void updatePoops() {
        boolean[] visiblePoop = tamas.getCurrentTama().getVisiblePoop();
        for (int i = 0; i < Tama.MAX_POOPS; i++) {
            ImageView poop = (ImageView) poops.getChildren().get(i);
            if(visiblePoop[i])
                poop.setVisible(true);
            else
                poop.setVisible(false);
        }
    }

    private void updateSpeech(){
        if(tamas.getCurrentTama().isHungry())
            speech.setVisible(false);
        else
            speech.setVisible(true);
    }
}
