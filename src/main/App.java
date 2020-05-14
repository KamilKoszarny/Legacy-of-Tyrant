package main;

import controller.isoView.IsoViewController;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Battle;
import model.BattleInitializer;
import model.IsoBattleLoop;
import model.TurnsTracker;
import model.character.Character;
import model.map.Map;
import sun.misc.Unsafe;
import viewIso.IsoViewer;
import viewIso.panel.PanelViewer;

import java.lang.reflect.Field;
import java.util.List;

public class App extends Application {

    public static final boolean FULL_MODE = true; //false: faster initialization
    public static final boolean TURN_MODE_FORCED = false;
    private static final int LOG_TIME_LEVEL = -1; //more: deeper log, -999 for no log

    public static final long START_TIME = System.nanoTime();
    public static final long[] time = new long[10];

    public static void main(String[] args) {
        disableWarning();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Map map = BattleInitializer.initMap();
        List<Character> characters = BattleInitializer.initCharacters(map);
        new Battle(map, characters, Color.YELLOW, Color.RED);
        startBattleIso(primaryStage);

        if (TURN_MODE_FORCED)
            TurnsTracker.startTurnMode();
    }

    private void startBattleIso(Stage primaryStage) throws Exception {
        IsoBattleLoop isoBattleLoop = new IsoBattleLoop();
        IsoViewController isoViewController = new IsoViewController(primaryStage);
        new IsoViewer(isoViewController.getMapCanvas());
        new PanelViewer(isoViewController.getPanel());

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

    public static void resetTime(int level) {
        time[level + 1] = System.nanoTime();
    }

    public static void showAndResetTime(String text, int level) {
        StringBuilder spaces = new StringBuilder("");
        for (int i = 0; i < level; i++) {
            spaces.append("   ");
        }
        if (level <= LOG_TIME_LEVEL) {
            double timeDelta = (System.nanoTime() - time[level + 1]) / 1000000.;
            if (timeDelta < 50)
                System.out.println(spaces + text + ": " + timeDelta + " ms");
            else
                System.err.println(spaces + text + ": " + timeDelta + " ms");
        }
        time[level + 1] = System.nanoTime();
    }
}
