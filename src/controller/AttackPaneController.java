package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.AttackCalculator;
import model.character.Character;

import java.io.IOException;
import java.util.List;

public class AttackPaneController {

    @FXML
    private Label attackerLabel;
    @FXML
    private TableView<Character> targetsTable;
    @FXML
    private TextField scoreTextField;
    @FXML
    private Button attackButton;
    @FXML
    private Button cancelButton;

    FXMLLoader fxmlLoader;
    Stage stage;
    BattlePaneController battlePaneController;

    Character attacker;
    List<Character> targets;

    public AttackPaneController(BattlePaneController battlePaneController, Character attacker, List<Character> targets) {
        this.battlePaneController = battlePaneController;
        this.attacker = attacker;
        this.targets = targets;
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/AttackWindow.fxml"));
            fxmlLoader.setController(this);

            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            stage = new Stage();
            stage.setTitle("Attack");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize(){
        initAttackerLabel();
        initTargetsTableView();
        initScoreTextField();
        initAttackButton();
        initCancelButton();
    }

    private void initAttackerLabel(){
        attackerLabel.setText("Attacker: " + attacker.getName());
    }

    private void initTargetsTableView(){
        targetsTable.setEditable(true);
        targetsTable.setItems(FXCollections.observableList(targets));
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Character, String>("name"));
        targetsTable.getColumns().add(nameColumn);

        for (Character target: targets) {
            target.setChanceToBeHit(
                    AttackCalculator.calcChanceToHit(attacker, target));
        }
        Effects.createEditableDoubleColumn(targetsTable, "chanceToBeHit");

        markTargetedCharacter();
    }

    private void initScoreTextField(){
        Effects.forceNumericTextField(scoreTextField);
    }

    private void initAttackButton(){
        attackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!scoreTextField.getText().equals("")){
                    Character target = targetsTable.getSelectionModel().getSelectedItem();
                    double attackScore = Double.parseDouble(scoreTextField.getText());

                    new DefencePaneController(battlePaneController, attacker, target, attackScore);

                    stage.close();
                    fxmlLoader.setController(battlePaneController);
                    Effects.refreshTable(battlePaneController.getCharactersTable());
                }
            }
        });
    }

    private void initCancelButton(){
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
                fxmlLoader.setController(battlePaneController);
            }
        });

    }

    private void markTargetedCharacter(){
        targetsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            Character lastTargeted = targets.get(0);
            for (Character character: targets) {
                if (character.isTargeted()) {
                    lastTargeted = character;
                    character.setTargeted(false);
                }
            }
            obs.getValue().setTargeted(true);

            battlePaneController.getMapDrawer().drawPointProximity(lastTargeted.getPosition());
            battlePaneController.getMapDrawer().drawCharacters(battlePaneController.getCharacters());
        });
    }
}
