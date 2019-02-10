package viewIso.panel;

import controller.isoView.isoPanel.Panel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.shape.Polygon;
import model.Battle;
import model.TurnsTracker;

public class NextTurnButtonViewer {

    private static Button nextTurnButton;

    NextTurnButtonViewer(Panel panel) {
        NextTurnButtonViewer.nextTurnButton = panel.getNextTurnButton();

        initNextTurnButtonLook();
    }

    private static void initNextTurnButtonLook() {
        nextTurnButton.setText("Next\nTurn");
        nextTurnButton.setAlignment(Pos.BASELINE_LEFT);
        nextTurnButton.setShape(new Polygon(0, 1, 2, 1, 3, 0, 2, -1, 0, -1));
        nextTurnButton.setPickOnBounds(false);
        nextTurnButton.setPrefWidth(60);
        nextTurnButton.setPrefHeight(40);
    }

    public static void refreshNextTurnButtonLook() {
        if (!Battle.isTurnMode() || !Battle.getPlayerColor().equals(TurnsTracker.getActiveCharacter().getColor()))
            nextTurnButton.setVisible(false);
        else {
            nextTurnButton.setVisible(true);
            if (TurnsTracker.activeCharChosen()) {
                if (TurnsTracker.activeCharMoved())
                    nextTurnButton.setText("Next\nTurn");
                else
                    nextTurnButton.setText("Wait\n[" + TurnsTracker.WAIT_AP_COST + "]");
            } else
                nextTurnButton.setText("Active\nChar");
        }
    }
}
