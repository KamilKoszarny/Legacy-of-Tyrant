package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import model.*;
import model.character.Character;
import model.character.CharacterType;
import model.map.Map;
import model.map.MapGenerator;
import model.map.Terrain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class BattlePaneController {


    @FXML
    private Canvas mapCanvas;
    @FXML
    private TableView<Character> charactersTable;
    @FXML
    private Button attackButton;
    @FXML
    private Button newCharacterButton;
    @FXML
    private Button newTurnButton;

    BattlePaneController thisController = this;
    MapDrawer mapDrawer;
    Map map;
    List<Character> characters = new ArrayList<>();

    @FXML
    void initialize(){
        initParams();
        initCanvas();
        initCharactersTable();
        initAttackButton();
        initNewCharacterButton();
        initNewTurnButton();
        drawAll();
    }

    private void initParams(){
        Terrain.GROUND.setIntensity(30);
        Terrain.GRASS.setIntensity(100);
        Terrain.BUSH.setIntensity(1);
        Terrain.TREES.setIntensity(100);

        map = MapGenerator.generateMap(50, 50);

        characters = new ArrayList<>();
        Character czlehulec = new Character(CharacterType.HUMAN, "Człehulec", Color.GRAY);
        czlehulec.setSpeed(2.8);
        czlehulec.setDmgMin(2.);
        czlehulec.setDmgMax(6.);
        czlehulec.setHitPoints(6.);
        czlehulec.setRange(2.);
        Character slimako = new Character(CharacterType.DWARF, "Slimako", Color.BLACK);
        slimako.setSpeed(1.2);
        slimako.setDmgMin(3.);
        slimako.setDmgMax(4.);
        slimako.setHitPoints(9.);
        slimako.setRange(1.);
        characters.add(czlehulec);
        characters.add(slimako);
    }

    private void initCanvas(){
        mapCanvas.setHeight(50 * 10);
        mapCanvas.setWidth(50 * 10);

        canvasNewCharMode();
    }

    void canvasGameMode(){
        mapCanvas.setOnMousePressed((event -> {
            Point clickPoint = new Point((int)event.getX(),  (int)event.getY());

            for (Character character: characters) {
                if (character.isChosen()) {
                    mapDrawer.drawPointProximity(character.getPosition());
                    if(character.getMsLeft() > 0) {
                        MoveCalculator.moveCharacter(character, clickPoint, map);
                    }
                }
            }

            mapDrawer.drawCharacters(characters);
            Effects.refreshTable(charactersTable);
        }));
    }

    void canvasNewCharMode(){
        mapCanvas.setOnMousePressed((event -> {
            Point clickPoint = new Point((int)event.getX(),  (int)event.getY());

            for (Character character: characters) {
                if (character.isChosen()) {
                    mapDrawer.drawPointProximity(character.getPosition());
                    character.setPosition(clickPoint);
                }
            }

            mapDrawer.drawCharacters(characters);
        }));
    }


    private void initCharactersTable(){
        charactersTable.setEditable(true);
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Character, String>("name"));
        TableColumn msLeftColumn = new TableColumn("ms left");
        msLeftColumn.setCellValueFactory(new PropertyValueFactory<Character, Integer>("msLeft"));
        charactersTable.getColumns().addAll(nameColumn, msLeftColumn);

        Effects.createEditableDoubleColumn(charactersTable, "speed");
        Effects.createEditableDoubleColumn(charactersTable, "dmgMin");
        Effects.createEditableDoubleColumn(charactersTable, "dmgMax");
        Effects.createEditableDoubleColumn(charactersTable, "hitPoints");
        Effects.createEditableDoubleColumn(charactersTable, "range");

        markChosenCharacter();
    }

    private void initAttackButton(){
        Effects.addButtonShadow(attackButton);

        attackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                List<Character> charactersInRange = new ArrayList<>();
                Character chosenCharacter = null;
                for (Character character: characters)
                    if (character.isChosen())
                        chosenCharacter = character;
                for (Character character: characters)
                    if (!character.isChosen() && AttackCalculator.isInRange(chosenCharacter, character))
                        charactersInRange.add(character);
                new AttackPaneController(thisController, chosenCharacter, charactersInRange);

            }
        });
    }

    private void initNewCharacterButton(){
        Effects.addButtonShadow(newCharacterButton);
        newCharacterButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                new NewCharPaneController(thisController);
            }
        });
    }

    private void initNewTurnButton(){
        Effects.addButtonShadow(newTurnButton);
        newTurnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (Character character: characters) {
                    character.setMsLeft(character.getMsLeft() + 1000);
                    if(character.getMsLeft() > 1000)
                        character.setMsLeft(1000);
                }
                Effects.refreshTable(charactersTable);
                canvasGameMode();
            }
        });
    }

    private void drawAll(){
        GraphicsContext g = mapCanvas.getGraphicsContext2D();
        mapDrawer = new MapDrawer(g, map);
        mapDrawer.drawMap();
        mapDrawer.drawCharacters(characters);
        mapDrawer.listCharacters(characters, charactersTable);
    }



    void refreshCharactersTable(){
        Effects.refreshTable(charactersTable);
    }

    void addCharacter(Character character){
        characters.add(character);
        charactersTable.getItems().add(character);
    }

    private void markChosenCharacter(){
        charactersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            Character lastChosen = characters.get(0);
            for (Character character: characters) {
                if (character.isChosen()) {
                    lastChosen = character;
                    character.setChosen(false);
                }
            }
            obs.getValue().setChosen(true);

            mapDrawer.drawPointProximity(lastChosen.getPosition());
            mapDrawer.drawCharacters(characters);
        });
    }

    public MapDrawer getMapDrawer() {
        return mapDrawer;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public TableView<Character> getCharactersTable() {
        return charactersTable;
    }
}
