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
    private Button newTurnButton;
    @FXML
    private Button newCharacterButton;

    MapDrawer mapDrawer;
    Map map;
    List<Character> characters = new ArrayList<>();

    @FXML
    void initialize(){
        initParams();
        initCanvas();
        initCharactersTable();
        initNewTurnButton();
        initNewCharacterButton();
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
        czlehulec.setDmgMin(2);
        czlehulec.setDmgMax(6);
        czlehulec.setHitPoints(6);
        Character slimako = new Character(CharacterType.DWARF, "Slimako", Color.BLACK);
        slimako.setSpeed(1.2);
        slimako.setDmgMin(3);
        slimako.setDmgMax(4);
        slimako.setHitPoints(9);
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
//            System.out.println(x.toString() + " " + y.toString());

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
//            System.out.println(x.toString() + " " + y.toString());

            for (Character character: characters) {
                if (character.isChosen()) {
                    mapDrawer.drawPointProximity(character.getPosition());
                    character.setPosition(clickPoint);
                }
            }

            mapDrawer.drawCharacters(characters);
//            canvasGameMode();
        }));
    }

    private void initCharactersTable(){
        charactersTable.setEditable(true);
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Character, String>("name"));
        TableColumn speedColumn = new TableColumn("Speed");
        speedColumn.setCellValueFactory(new PropertyValueFactory<Character, Double>("speed"));
        TableColumn dmgMinColumn = new TableColumn("DmgMin");
        dmgMinColumn.setCellValueFactory(new PropertyValueFactory<Character, Double>("dmgMin"));
        TableColumn dmgMaxColumn = new TableColumn("DmgMax");
        dmgMaxColumn.setCellValueFactory(new PropertyValueFactory<Character, Double>("dmgMax"));
        TableColumn hitPointsColumn = new TableColumn("HP");
        hitPointsColumn.setCellValueFactory(new PropertyValueFactory<Character, String>("hitPoints"));
        hitPointsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        hitPointsColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Character, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Character, String> cEE) {
                        (cEE.getTableView().getItems().get(cEE.getTablePosition().getRow())).
                                setHitPoints(cEE.getNewValue());
                    }
                }
        );

        TableColumn msLeftColumn = new TableColumn("ms left");
        msLeftColumn.setCellValueFactory(new PropertyValueFactory<Character, Integer>("msLeft"));
        charactersTable.getColumns().addAll(nameColumn, speedColumn, dmgMinColumn, dmgMaxColumn, hitPointsColumn, msLeftColumn);

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

    private void initNewCharacterButton(){
        Effects.addButtonShadow(newCharacterButton);
        BattlePaneController thisController = this;
        newCharacterButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                new NewCharPaneController(thisController);
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

    void foo(){
        System.out.println("BattlePaneController is it");
    }
}
