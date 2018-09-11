package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AttackCalculator;
import model.character.Character;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class DefencePaneController {

    @FXML
    private Button dodgeButton, parryButton, bounceButton, takeButton;
    @FXML
    private Label attackerLabel, defenderLabel, dodgeLabel, parryLabel, bounceLabel;
    @FXML
    private TextField scoreTextField;

    FXMLLoader fxmlLoader;
    Stage stage;
    BattlePaneController battlePaneController;

    Character attacker, defender;
    Double dodgeChance, parryChance, bounceChance, attackScore;

    public DefencePaneController(BattlePaneController battlePaneController, Character attacker, Character defender, double attackScore) {
        this.battlePaneController = battlePaneController;
        this.attacker = attacker;
        this.defender = defender;
        this.attackScore = attackScore;
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/DefenceWindow.fxml"));
            fxmlLoader.setController(this);

            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            stage = new Stage();
            stage.setTitle("Defence");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize(){
        initLabels();
        initDodgeButton();
        initParryButton();
        initBounceButton();
        initScoreTextField();
    }

    private void initLabels(){
        attackerLabel.setText(attacker.getName());
        defenderLabel.setText(defender.getName());
        dodgeChance = AttackCalculator.calcDodgeChance(defender);
        parryChance = AttackCalculator.calcParryChance(defender);
        bounceChance = AttackCalculator.calcBounceChance(defender);
        dodgeLabel.setText("Dodge chance: " + dodgeChance.toString());
        parryLabel.setText("Parry chance: " + parryChance.toString());
        bounceLabel.setText("Bounce chance: " + bounceChance.toString());
    }

    private void initDodgeButton(){
        Effects.addButtonShadow(dodgeButton);

        if(defender.isWasDodging())
            dodgeButton.setDisable(true);

        dodgeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Double.parseDouble(scoreTextField.getText()) > dodgeChance)
                    AttackCalculator.attackCharacter(attacker, defender, attackScore.intValue());

                defender.setMsLeft(defender.getMsLeft() - 500);
                defender.setVigor(defender.getDoubleVigor() - 1);
                defender.setWasDodging(true);

                stage.close();
                fxmlLoader.setController(battlePaneController);
                Effects.refreshTable(battlePaneController.getCharactersTable());
            }
        });
    }

    private void initParryButton(){
        Effects.addButtonShadow(parryButton);

        if(defender.isWasParrying())
            parryButton.setDisable(true);

        parryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Double.parseDouble(scoreTextField.getText()) > parryChance)
                    AttackCalculator.attackCharacter(attacker, defender, attackScore.intValue());

                defender.setMsLeft(defender.getMsLeft() - 200);
                defender.setVigor(defender.getDoubleVigor() - 1.5);
                defender.setWasParrying(true);

                stage.close();
                fxmlLoader.setController(battlePaneController);
                Effects.refreshTable(battlePaneController.getCharactersTable());
            }
        });
    }

    private void initBounceButton(){
        Effects.addButtonShadow(bounceButton);

        if(defender.isWasBouncing())
            bounceButton.setDisable(true);

        bounceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Double.parseDouble(scoreTextField.getText()) > bounceChance)
                    AttackCalculator.attackCharacter(attacker, defender, attackScore.intValue());

                Point start = defender.getPosition();
                Random r = new Random();
                Point stop = new Point(start.x + r.nextInt(3), start.y + r.nextInt(3));
                defender.setPosition(stop);
                defender.setMsLeft(defender.getMsLeft() - 600);
                defender.setVigor(defender.getDoubleVigor() - 1.2);
                defender.setWasBouncing(true);

                stage.close();
                fxmlLoader.setController(battlePaneController);
                Effects.refreshTable(battlePaneController.getCharactersTable());
            }
        });
    }

    private void initScoreTextField(){
        Effects.forceNumericTextField(scoreTextField);
    }
}
