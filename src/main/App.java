package main;

import controller.isoView.IsoViewController;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Battle;
import model.BattleInitializer;
import model.IsoBattleLoop;
import model.character.Character;
import model.map.Map;
import sun.misc.Unsafe;
import viewIso.IsoViewer;
import viewIso.panel.PanelViewer;

import java.lang.reflect.Field;
import java.util.List;

public class App extends Application {

    public static final boolean FULL_MODE = false;

    public static void main(String[] args) {
        disableWarning();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Map map = BattleInitializer.initMap();
        List<Character> characters = BattleInitializer.initCharacters(map);
        Battle battle = new Battle(map, characters, Color.YELLOW);
        startBattleIso(battle, primaryStage);
    }

    private void startBattleIso(Battle battle, Stage primaryStage) throws Exception {
        IsoBattleLoop isoBattleLoop = new IsoBattleLoop(battle);
        IsoViewController isoViewController = new IsoViewController(primaryStage);
        new IsoViewer(battle, isoViewController.getMapCanvas());
        new PanelViewer(isoViewController.getPanel(), battle.getCharacters());

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