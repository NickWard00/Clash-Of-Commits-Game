package ooga.view.screens;

import java.io.File;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Shape;
import ooga.controller.CollisionHandler;
import ooga.controller.Controller;
import ooga.model.Collision;
import ooga.view.BlockView;
import ooga.view.EntityView;
import ooga.view.HUD;
import ooga.view.MapView;
import ooga.view.MapWrapper;

import java.util.Map;

/**
 * @author Melanie Wang, Nick Ward, Mayari Merchant
 */

public class MainGameScreen extends SceneCreator {
    //TODO: refactor all "Screens" into subclasses of a screen superclass
    //TODO: refactor stackpane
    private MapWrapper mapWrapper;
    private MapView mapView;
    private boolean isPlaying = false;
    private int screenSize;
    private Map<String, EntityView> myViewEntities;
    private Group root;

    private BorderPane gameScreenPane;
    private ScrollPane background;

    private StackPane centerPaneConsolidated;

    private Pane characters;

    private HUD hud;
    private List<BlockView> obstacleList;

    private Media music;
    private Media walk;
    private MediaPlayer musicPlayer;
    private MediaPlayer walkPlayer;


    public MainGameScreen(){
        this.screenSize = getScreenSize();
    }

    public void startGamePlay(MapWrapper map, Map<String, EntityView> entities) {
        isPlaying = true;
        this.mapWrapper = map;
        mapView = new MapView(mapWrapper);
        myViewEntities = entities;
        music =new Media(new File(media.getString("lvl1")).toURI().toString());
        walk = new Media(new File(media.getString("walking")).toURI().toString());
        walkPlayer = new MediaPlayer(walk);
    }

    //make new scene
    @Override
    public Scene makeScene(){
        gameScreenPane = new BorderPane();
        background = new ScrollPane();
        centerPaneConsolidated = new StackPane();
        background.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        background.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        characters= new Pane();
        hud = new HUD();
        root = new Group();
        //root.getChildren().add(mapView.createMap());
        for (EntityView entity : myViewEntities.values()) {
            root.getChildren().add(entity);
        }
        characters.getChildren().add(root);
        background.setContent(mapView.createMap());
        centerPaneConsolidated.getChildren().addAll(background, characters);
        gameScreenPane.setCenter(centerPaneConsolidated);
        gameScreenPane.setTop(hud.makeHUD());
        Scene s = new Scene(gameScreenPane, screenSize, screenSize);
        s.getStylesheets().add(styles.getString("mainGameScreenCSS"));
        musicPlayer= new MediaPlayer(music);
        musicPlayer.setAutoPlay(true);
        return s;
    }


    public void removeEntityFromScene(String entityName){
        root.getChildren().remove(myViewEntities.get(entityName));
    }

    public StackPane getMapPane() {
        return this.centerPaneConsolidated;
    }

    public boolean isPlaying(){
        return isPlaying;
    }
    public void stopPlaying(){
        isPlaying = false;
    }

    public MediaPlayer getWalkPlayer() {
        return walkPlayer;
    }

    public void detectCollisions(Controller controller) {
//        for (BlockView block: )
        int counter = 0;
        obstacleList = mapView.getObstacleList();
        for (EntityView entity: myViewEntities.values()) {
            for (BlockView obstacle: obstacleList) {
//                counter++;
//                if (counter == 1) {
////                    System.out.println(obstacleList);
//                }
                if (myViewEntities.get("Hero1").localToScreen(myViewEntities.get("Hero1").getBoundsInLocal()).intersects(obstacle.getImageView().localToScreen(obstacle.getImageView().getBoundsInLocal()))) {
//                    Bounds a = obstacle.getImageView().localToScreen(obstacle.getImageView().getBoundsInLocal());
//                    System.out.println(obstacle.getImageView().localToScreen(obstacle.getImageView().getBoundsInLocal()));
//                    System.out.println(obstacleList.get(0).getImageView().localToScreen(obstacleList.get(0).getImageView().getBoundsInLocal()));
//                    System.out.println(a.equals(obstacleList.get(0).getImageView().localToScreen(obstacleList.get(0).getImageView().getBoundsInLocal())));
//                    System.out.println("Hello");
                    CollisionHandler handler = new CollisionHandler(controller);
                    //handler.collision(entity, obstacle);
                    handler.translateCollision(entity, obstacle);
                }
            }
        }

    }
}
