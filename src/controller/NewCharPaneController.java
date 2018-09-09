package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.StatsCalculator;
import model.character.Character;
import model.character.CharacterClass;
import model.character.CharacterGroup;
import model.character.CharacterType;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;

public class NewCharPaneController {

    @FXML
    private ComboBox<CharacterGroup> groupComboBox;
    @FXML
    private ComboBox<CharacterType> typeComboBox;
    @FXML
    private ComboBox<CharacterClass> classComboBox;
    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox<Color> colorComboBox;
    @FXML
    private Label strengthLabel, durabilityLabel, staminaLabel, armLabel, eyeLabel, agilityLabel, knowledgeLabel, focusLabel, charismaLabel;
    @FXML
    private Button calcAttributesButton;

    @FXML
    private TextField hitPointsTextField;
    @FXML
    private TextField dmgMinTextField;
    @FXML
    private TextField dmgMaxTextField;
    @FXML
    private TextField speedTextField;
    @FXML
    private TextField rangeTextField;
    @FXML
    private TextField chanceToHitTextField;
    @FXML
    private TextField attackDurationTextField;
    @FXML
    private TextField headArmorTextField, bodyArmorTextField, armsArmorTextField, legsArmorTextField;
    @FXML
    private Button createCharacterButton;

    FXMLLoader fxmlLoader;
    Stage stage;
    BattlePaneController battlePaneController;

    Character newCharacter;

    public NewCharPaneController(BattlePaneController battlePaneController) {
        this.battlePaneController = battlePaneController;
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/NewCharacterWindow.fxml"));
            fxmlLoader.setController(this);

            Scene scene = new Scene(fxmlLoader.load(), 600, 430);
            stage = new Stage();
            stage.setTitle("New Character");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        newCharacter = new Character();
    }

    @FXML
    void initialize(){
        initGroupAndTypeComboBoxes();
        initClassComboBox();
        initColorComboBox();
        initCalcAttributesButton();
        initCreateCharacterButton();
        initParamsTextFields();
    }

    private void initGroupAndTypeComboBoxes(){
        groupComboBox.getItems().setAll(CharacterGroup.values());
        groupComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                typeComboBox.getItems().setAll(
                        groupComboBox.getSelectionModel().getSelectedItem().
                        getBelongingTypes());
            }
        });
    }

    private void initClassComboBox(){
        classComboBox.getItems().setAll(CharacterClass.values());
    }

    private void initColorComboBox(){
        colorComboBox.getItems().setAll(
                Color.BLACK, Color.GRAY, Color.BLUE, Color.YELLOW, Color.GREEN, Color.ORANGE, Color.RED, Color.BROWN,
                Color.CYAN, Color.DARKBLUE, Color.PINK, Color.PURPLE, Color.VIOLET);
        colorComboBox.setConverter(new StringConverter<Color>() {
            @Override
            public String toString(Color color) {
                for (Field field: Color.class.getFields()) {
                    if (field.getType() == Color.class){
                        try {
                            if (field.get(null) == color)
                                return field.getName();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return "no color";
            }

            @Override
            public Color fromString(String s) {
                return null;
            }
        });
    }

    private void initCalcAttributesButton(){
        Effects.addButtonShadow(calcAttributesButton);
        calcAttributesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                newCharacter.setType(typeComboBox.getSelectionModel().getSelectedItem());
                newCharacter.setCharClass(classComboBox.getSelectionModel().getSelectedItem());

                StatsCalculator.calcCharStats(newCharacter);

                strengthLabel.setText(newCharacter.getStrength());
                durabilityLabel.setText(newCharacter.getDurability());
                staminaLabel.setText(newCharacter.getStamina());
                armLabel.setText(newCharacter.getArm());
                eyeLabel.setText(newCharacter.getEye());
                agilityLabel.setText(newCharacter.getAgility());
                knowledgeLabel.setText(newCharacter.getKnowledge());
                focusLabel.setText(newCharacter.getFocus());
                charismaLabel.setText(newCharacter.getCharisma());

                hitPointsTextField.setText(newCharacter.getHitPoints());
                dmgMinTextField.setText(newCharacter.getDmgMin());
                dmgMaxTextField.setText(newCharacter.getDmgMax());
                speedTextField.setText(newCharacter.getSpeed());
                rangeTextField.setText(newCharacter.getRange());
                chanceToHitTextField.setText(newCharacter.getChanceToHit());
                attackDurationTextField.setText(newCharacter.getAttackDuration());
                headArmorTextField.setText(newCharacter.getHeadArmor());
                bodyArmorTextField.setText(newCharacter.getBodyArmor());
                armsArmorTextField.setText(newCharacter.getArmsArmor());
                legsArmorTextField.setText(newCharacter.getLegsArmor());
            }
        });
    }




    private void initCreateCharacterButton(){
        Effects.addButtonShadow(createCharacterButton);

        createCharacterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                newCharacter.setType(typeComboBox.getSelectionModel().getSelectedItem());
                newCharacter.setCharClass(classComboBox.getSelectionModel().getSelectedItem());
                newCharacter.setName(nameTextField.getText());
                newCharacter.setColor(colorComboBox.getValue());
                newCharacter.setSpeed(speedTextField.getText());
                newCharacter.setDmgMin(dmgMinTextField.getText());
                newCharacter.setDmgMax(dmgMaxTextField.getText());
                newCharacter.setHitPoints(hitPointsTextField.getText());
                newCharacter.setRange(rangeTextField.getText());
                newCharacter.setAttackDuration(attackDurationTextField.getText());
                newCharacter.setHeadArmor(headArmorTextField.getText());
                newCharacter.setBodyArmor(bodyArmorTextField.getText());
                newCharacter.setArmsArmor(armsArmorTextField.getText());
                newCharacter.setLegsArmor(legsArmorTextField.getText());

                newCharacter.setChosen(true);
                newCharacter.setPosition(new Point(45,45));

                stage.close();
                fxmlLoader.setController(battlePaneController);
                battlePaneController.canvasNewCharMode();
                battlePaneController.addCharacter(newCharacter);
            }
        });
    }

    private void initParamsTextFields(){
        Effects.forceNumericTextField(hitPointsTextField);
        Effects.forceNumericTextField(dmgMinTextField);
        Effects.forceNumericTextField(dmgMaxTextField);
        Effects.forceNumericTextField(speedTextField);
        Effects.forceNumericTextField(rangeTextField);
        Effects.forceNumericTextField(chanceToHitTextField);
        Effects.forceNumericTextField(attackDurationTextField);
        Effects.forceNumericTextField(headArmorTextField);
        Effects.forceNumericTextField(bodyArmorTextField);
        Effects.forceNumericTextField(armsArmorTextField);
        Effects.forceNumericTextField(legsArmorTextField);
    }
}
