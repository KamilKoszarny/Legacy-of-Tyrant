package controller.isoview.panel;

import javafx.scene.control.Button;
import model.TurnsTracker;

class NextTurnButtonController {

    private static Button nextTurnButton;

    NextTurnButtonController(Panel panel) {
        NextTurnButtonController.nextTurnButton = panel.getNextTurnButton();

        initNextTurnButtonAction();
    }

    private static void initNextTurnButtonAction() {
        nextTurnButton.setOnAction(actionEvent -> {
            if (TurnsTracker.activeCharChosen())
                TurnsTracker.nextTurn();
            else
                TurnsTracker.chooseActiveChar();
        });
    }
}
