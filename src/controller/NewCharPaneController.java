package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
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
    private TextField speedTextField;
    @FXML
    private TextField dmgMinTextField;
    @FXML
    private TextField dmgMaxTextField;
    @FXML
    private TextField hitPointsTextField;
    @FXML
    private TextField rangeTextField;
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

            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
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
        Effects.forceNumericTextField(speedTextField);
        Effects.forceNumericTextField(dmgMinTextField);
        Effects.forceNumericTextField(dmgMaxTextField);
        Effects.forceNumericTextField(hitPointsTextField);
        Effects.forceNumericTextField(rangeTextField);
    }
}
