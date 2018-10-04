package main;

import controller.isoView.IsoViewController;
import controller.javaFX.BattlePaneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Battle;
import model.IsoBattleLoop;
import model.character.Character;
import model.map.Map;
import sun.misc.Unsafe;
import viewIso.IsoViewer;

import java.lang.reflect.Field;
import java.util.List;

public class App extends Application {

    public static void main(String[] args) {
//        System.out.println(App.class.getResource("/sprites/flare/demo/"));
//
//        System.out.println(App.class.getResource("/sprites/flare/demo/vesuvvio.png"));
        disableWarning();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Battle battle = new Battle();
        Map map = battle.getMap();
        List<Character> characters = battle.getCharacters();
//        startBattleFX(primaryStage, map);
        startBattleIso(primaryStage, map, characters);
    }

    private void startBattleFX(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/fxml/MainWindow.fxml"));

        BattlePaneController battlePaneController = new BattlePaneController();
        fxmlLoader.setController(battlePaneController);

        Pane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Random Adventures Helper");
        primaryStage.show();
    }

    private void startBattleIso(Stage primaryStage, Map map, List<Character> characters) throws Exception {
        IsoBattleLoop isoBattleLoop = new IsoBattleLoop();
        IsoViewController isoViewController = new IsoViewController(primaryStage, isoBattleLoop, map, characters);
        IsoViewer isoViewer = isoViewController.getIsoViewer();
        isoBattleLoop.setIsoViewer(isoViewer);
        isoBattleLoop.start();
    }

    //Hide warning “Illegal reflective access” in java 9
    private static void disableWarning() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe u = (Unsafe) theUnsafe.get(null);

            Class cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception e) {
            // ignore
        }
    }
}