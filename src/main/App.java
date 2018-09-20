package main;

import controller.isoView.IsoViewController;
import controller.javaFX.BattlePaneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Battle;
import model.map.Map;
import sun.misc.Unsafe;

import java.awt.*;
import java.lang.reflect.Field;

public class App extends Application {

    public static void main(String[] args) {
        disableWarning();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Battle battle = new Battle();
        Map map = battle.getMap();
//        startBattleFX(primaryStage, map);
        startBattleIso(primaryStage, map);
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

    private void startBattleIso(Stage primaryStage, Map map) throws Exception {
        IsoViewController isoViewController = new IsoViewController(primaryStage, map);
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