package controller;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.character.Character;
import model.character.CharacterType;

import java.io.IOException;
import java.util.Arrays;

public class NewCharPaneController {


    @FXML
    private ComboBox<CharacterType> typeComboBox;

    @FXML
    private TextField speedTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button createCharacterButton;

    Character newCharacter;
    BattlePaneController battlePaneController;

    public NewCharPaneController(BattlePaneController battlePaneController) {
        this.battlePaneController = battlePaneController;
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/NewCharacterWindow.fxml"));
            fxmlLoader.setController(this);
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
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
        initTypeChoiceBox();
        initCreateCharacterButton();
    }

    private void initTypeChoiceBox(){
        typeComboBox.getItems().setAll(CharacterType.values());
    }

    private void initCreateCharacterButton(){
        Effects.addButtonShadow(createCharacterButton);

        createCharacterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                newCharacter.setType(typeComboBox.getSelectionModel().getSelectedItem());
                newCharacter.setName(nameTextField.getText());
                newCharacter.setSpeed(Double.parseDouble(speedTextField.getText()));

                battlePaneController.addCharacter(newCharacter);
                battlePaneController.refreshCharactersTable();
            }
        });
    }
}
