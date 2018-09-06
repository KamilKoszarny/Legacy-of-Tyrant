package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.character.Character;
import model.character.CharacterType;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;

public class NewCharPaneController {


    @FXML
    private ComboBox<CharacterType> typeComboBox;
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
    private Button createCharacterButton;

    FXMLLoader fxmlLoader;
    Stage stage;

    Character newCharacter;
    BattlePaneController battlePaneController;

    public NewCharPaneController(BattlePaneController battlePaneController) {
        this.battlePaneController = battlePaneController;
        Parent root;
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/NewCharacterWindow.fxml"));
            fxmlLoader.setController(this);
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
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
        initTypeComboBox();
        initColorComboBox();
        initCreateCharacterButton();
    }

    private void initTypeComboBox(){
        typeComboBox.getItems().setAll(CharacterType.values());
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
                newCharacter.setName(nameTextField.getText());
                newCharacter.setColor(colorComboBox.getValue());
                newCharacter.setSpeed(Double.parseDouble(speedTextField.getText()));
                newCharacter.setDmgMin(Double.parseDouble(dmgMinTextField.getText()));
                newCharacter.setDmgMax(Double.parseDouble(dmgMaxTextField.getText()));
                newCharacter.setHitPoints(Double.parseDouble(hitPointsTextField.getText()));

                newCharacter.setChosen(true);
                newCharacter.setPosition(new Point(45,45));

                stage.close();
                fxmlLoader.setController(battlePaneController);
                battlePaneController.canvasNewCharMode();
                battlePaneController.addCharacter(newCharacter);
            }
        });
    }
}
