package ooga.view;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

//this screen is for when a player wishes to start a brand new game.
public class OpenNewGameScreen extends SceneCreator {
    private GameSlot slot1;
    private GameSlot slot2;
    private GameSlot slot3;
    private StackPane background;
    private ResourceBundle labels;
    private ResourceBundle styles;
    private Stage stage;
    private int screenSize;

    public OpenNewGameScreen(Stage stage){
        this.stage = stage;
        labels = getLabels();
        styles = getStyles();
        screenSize = getScreenSize();
    }

    public Scene makeScene(){
        background = new StackPane();
        slot1 = new GameSlot(labels.getString("game1"), labels);
        slot2 = new GameSlot(labels.getString("game2"), labels);
        slot3 = new GameSlot(labels.getString("game3"), labels);
        VBox slots = new VBox(slot1,slot2,slot3);
        slots.getStyleClass().add("vbox");
        slots.setAlignment(Pos.CENTER);
        background.setAlignment(Pos.CENTER);
        background.getChildren().add(slots);
        Scene s = new Scene(background, screenSize, screenSize);
        handleEvents();
        s.getStylesheets().add(styles.getString("openNewGameCSS"));
        return s;
    }

    //parsing of files should occur here
    public void handleEvents(){
        slot1.setOnMouseClicked(event -> {
            MainGameScreen mainGameScreen = new MainGameScreen();
            mainGameScreen.startGamePlay();
            stage.setScene(mainGameScreen.makeScene());

        });
        slot2.setOnMouseClicked(event -> {

        });
        slot3.setOnMouseClicked(event -> {

        });
    }
}
