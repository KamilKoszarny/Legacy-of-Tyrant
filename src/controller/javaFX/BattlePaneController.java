package controller.javaFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import model.*;
import model.armor.*;
import model.character.Character;
import model.character.CharacterClass;
import model.character.CharacterType;
import model.map.*;
import model.weapon.Weapon;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BattlePaneController {

    @FXML
    private Canvas mapCanvas;
    @FXML
    private TableView<Character> charactersTable;
    @FXML
    private Button attackButton, changeWeaponButton, getReadyButton, restButton, newCharacterButton, newTurnButton, new3TurnsButton, new10TurnsButton;
    @FXML
    private CheckBox runCheckBox, sneakingCheckBox;

    private BattlePaneController thisController = this;
    private MapDrawer mapDrawer;
    private Map map;
    private List<Character> characters = new ArrayList<>();

    @FXML
    void initialize(){
        initMap();
        initCharacters();
        initCanvas();
        initCharactersTable();
        initAttackButton();
        initChangeWeaponButton();
        initGetReadyButton();
        initRestButton();
        initRunCheckBox();
        initSneakingCheckBox();
        initNewCharacterButton();
        initNewTurnButton();
        initNew3TurnsButton();
        initNew10TurnsButton();
        drawAll();
    }

    private void initMap(){
        boolean[] roadSides = {true, false, true, true};
        map = new Map(50, 50, MapType.GRASSLAND, MapHeightType.FLAT, roadSides);
    }

    private void initCharacters(){
        characters = new ArrayList<>();
        Character czlehulec = new Character("Człehulec", Color.GRAY, CharacterType.HUMAN, CharacterClass.RASCAL);
        czlehulec.setCurrentPA(13, 15, 15, 36, 40, 29, 15, 5, 25);
        czlehulec.setWeapons(new Weapon[]{Weapon.HUNTER_BOW, Weapon.TWO_HAND_SWORD});
        czlehulec.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.LEATHER_ARMOR, Helmet.NOTHING, Gloves.RAG_GLOVES, Boots.NOTHING, Belt.NOTHING});
        czlehulec.setEye(czlehulec.getDoubleEye() - 5);
        StatsCalculator.calcCharSA(czlehulec, false);

        Character slimako = new Character("Slimako", Color.BLACK, CharacterType.DWARF, CharacterClass.ADEPT);
        slimako.setCurrentPA(25, 25, 13, 10, 15, 13, 44, 36, 20);
        slimako.setWeapons(new Weapon[]{Weapon.BIG_ADZE, Weapon.SPEAR});
        slimako.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.NOTHING, Helmet.CASQUE, Gloves.NOTHING, Boots.NOTHING, Belt.LEATHER_BELT});
        StatsCalculator.calcCharSA(slimako, false);

        Character skowronka = new Character("Skowronka", Color.RED, CharacterType.DWARF, CharacterClass.BULLY);
        skowronka.setCurrentPA(39, 35, 25, 10, 15, 5, 10, 20, 10);
        skowronka.setWeapons(new Weapon[]{Weapon.CHOPPER, Weapon.SPIKE_CLUB});
        skowronka.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.GAMBISON, Helmet.NOTHING, Gloves.NOTHING, Boots.NOTHING, Belt.NOTHING});
        StatsCalculator.calcCharSA(skowronka, false);

        Character irith = new Character("Irith", Color.BLUE, CharacterType.ELF, CharacterClass.RASCAL);
        irith.setCurrentPA(11, 16, 24, 40, 31, 38, 15, 12, 13);
        irith.setWeapons(new Weapon[]{Weapon.SHORT_BOW, Weapon.DAGGER});
        irith.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.NOTHING, Helmet.NOTHING, Gloves.NOTHING, Boots.NOTHING, Belt.NOTHING});
        StatsCalculator.calcCharSA(irith, false);

        characters.addAll(new ArrayList<>(Arrays.asList(czlehulec, slimako, skowronka, irith)));

    }

    private void initCanvas(){
        mapCanvas.setHeight(50 * 10);
        mapCanvas.setWidth(50 * 10);

        canvasNewCharMode();
    }

    void canvasGameMode(){
        mapCanvas.setOnMousePressed((event -> {
            Point clickPoint = new Point((int)(event.getX() / Map.RESOLUTION_M * Map.M_PER_PIX),
                    (int)(event.getY() / Map.RESOLUTION_M * Map.M_PER_PIX));

            for (Character character: characters) {
                if (character.isChosen()) {
                    mapDrawer.drawPointProximity(character.getPosition());
                    if(character.getDoubleMsLeft() > 0) {
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
            Point clickPoint = new Point((int)(event.getX() / Map.RESOLUTION_M * Map.M_PER_PIX),
                    (int)(event.getY() / Map.RESOLUTION_M * Map.M_PER_PIX));

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
        TableColumn typeColumn = new TableColumn("Type");
        typeColumn.setPrefWidth(40);
        typeColumn.setCellValueFactory(new PropertyValueFactory<Character, String>("type"));
        TableColumn classColumn = new TableColumn("Class");
        classColumn.setPrefWidth(40);
        classColumn.setCellValueFactory(new PropertyValueFactory<Character, String>("charClass"));
        TableColumn weaponColumn = new TableColumn("Weapon");
        weaponColumn.setPrefWidth(60);
        weaponColumn.setCellValueFactory(new PropertyValueFactory<Character, String>("weapon"));
        charactersTable.getColumns().addAll(nameColumn, typeColumn, classColumn, weaponColumn);

        Effects.createEditableDoubleColumn(charactersTable, "msLeft");
        Effects.createEditableDoubleColumn(charactersTable, "reflex");
        Effects.createEditableDoubleColumn(charactersTable, "hitPoints");
        Effects.createEditableDoubleColumn(charactersTable, "vigor");
        Effects.createEditableDoubleColumn(charactersTable, "dmgMin");
        Effects.createEditableDoubleColumn(charactersTable, "dmgMax");
        Effects.createEditableDoubleColumn(charactersTable, "attackDuration");
        Effects.createEditableDoubleColumn(charactersTable, "speed");
        Effects.createEditableDoubleColumn(charactersTable, "range");
        Effects.createEditableDoubleColumn(charactersTable, "chanceToHit");
        Effects.createEditableDoubleColumn(charactersTable, "headArmor");
        Effects.createEditableDoubleColumn(charactersTable, "bodyArmor");
        Effects.createEditableDoubleColumn(charactersTable, "armsArmor");
        Effects.createEditableDoubleColumn(charactersTable, "legsArmor");
        Effects.createEditableDoubleColumn(charactersTable, "strength");
        Effects.createEditableDoubleColumn(charactersTable, "durability");
        Effects.createEditableDoubleColumn(charactersTable, "stamina");
        Effects.createEditableDoubleColumn(charactersTable, "arm");
        Effects.createEditableDoubleColumn(charactersTable, "eye");
        Effects.createEditableDoubleColumn(charactersTable, "agility");
        Effects.createEditableDoubleColumn(charactersTable, "knowledge");
        Effects.createEditableDoubleColumn(charactersTable, "focus");
        Effects.createEditableDoubleColumn(charactersTable, "charisma");

        markChosenCharacter();
        charactersTable.setRowFactory(tv -> new TableRow<Character>() {
            @Override
            public void updateItem(Character character, boolean empty) {
                super.updateItem(character, empty) ;
                if (character == null) {
                    setStyle("");
                } else {
                    Color color = character.getColor();
                    String cssColor = String.format("#%02x%02x%02x",
                            (int) (255 * color.getRed()),
                            (int) (255 * color.getGreen()),
                            (int) (255 * color.getBlue()));
                    setStyle("-fx-border-color: " + cssColor + "; -fx-border-width: 1.5;");
                }
            }
        });
    }

    private void initAttackButton(){
        Effects.addButtonShadow(attackButton);

        attackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (charactersTable.getSelectionModel().getSelectedItem().getDoubleMsLeft() >= 0) {
                    List<Character> charactersInRange = new ArrayList<>();
                    Character chosenCharacter = null;
                    for (Character character : characters)
                        if (character.isChosen())
                            chosenCharacter = character;
                    chosenCharacter.setReady(false);
                    for (Character character : characters)
                        if (!character.isChosen() && AttackCalculator.isInRange(chosenCharacter, character))
                            charactersInRange.add(character);
                    new AttackPaneController(thisController, chosenCharacter, charactersInRange);
                }
            }
        });
    }

    private void initChangeWeaponButton(){
        Effects.addButtonShadow(changeWeaponButton);

        changeWeaponButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Character chosenCharacter = null;
                for (Character character : characters)
                    if (character.isChosen())
                        chosenCharacter = character;
                if(chosenCharacter.getDoubleMsLeft() >= 0) {
                    chosenCharacter.setMsLeft(chosenCharacter.getDoubleMsLeft() - 1000);
                    chosenCharacter.setChosenWeapon((chosenCharacter.getChosenWeapon() + 1) % 2);
                    StatsCalculator.calcCharSA(chosenCharacter, true);
                    Effects.refreshTable(charactersTable);
                }
            }
        });

    }

    private void initGetReadyButton(){
        Effects.addButtonShadow(getReadyButton);

        getReadyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Character chosenCharacter = null;
                for (Character character : characters)
                    if (character.isChosen())
                        chosenCharacter = character;
                if(chosenCharacter.getDoubleMsLeft() >= 0 && !chosenCharacter.isReady()) {
                    chosenCharacter.setMsLeft(chosenCharacter.getDoubleMsLeft() - 500);
                    chosenCharacter.setVigor(chosenCharacter.getDoubleVigor() + 3);
                    if(chosenCharacter.getDoubleVigor() >= 60)
                        chosenCharacter.setVigor(60);
                    chosenCharacter.setReady(true);
                    Effects.refreshTable(charactersTable);
                }
            }
        });
    }

    private void initRestButton(){
        Effects.addButtonShadow(restButton);

        restButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Character chosenCharacter = null;
                for (Character character : characters)
                    if (character.isChosen())
                        chosenCharacter = character;
                if(chosenCharacter.getDoubleMsLeft() >= 0) {
                    chosenCharacter.setMsLeft(chosenCharacter.getDoubleMsLeft() - 1000);
                    chosenCharacter.setVigor(chosenCharacter.getDoubleVigor() + 10);
                    if(chosenCharacter.getDoubleVigor() >= 60)
                        chosenCharacter.setVigor(60);
                    chosenCharacter.setReady(true);
                    Effects.refreshTable(charactersTable);
                }
            }
        });
    }

    private void initRunCheckBox(){
        runCheckBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Character chosenCharacter = null;
                for (Character character : characters)
                    if (character.isChosen())
                        chosenCharacter = character;
                if(runCheckBox.isSelected())
                    chosenCharacter.setRunning(true);
                else
                    chosenCharacter.setRunning(false);

            }
        });
    }

    private void initSneakingCheckBox(){
        sneakingCheckBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Character chosenCharacter = null;
                for (Character character : characters)
                    if (character.isChosen())
                        chosenCharacter = character;
                if(sneakingCheckBox.isSelected())
                    chosenCharacter.setSneaking(true);
                else
                    chosenCharacter.setSneaking(false);

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
                nextTurnReset(1000);
            }
        });
    }

    private void initNew3TurnsButton(){
        Effects.addButtonShadow(new3TurnsButton);
        new3TurnsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                nextTurnReset(3000);
            }
        });
    }

    private void initNew10TurnsButton(){
        Effects.addButtonShadow(new10TurnsButton);
        new10TurnsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                nextTurnReset(10000);
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
                    lastChosen.setChosen(false);
                }
            }
            Character nowChosen = obs.getValue();
            nowChosen.setChosen(true);
            runCheckBox.setSelected(nowChosen.isRunning());
            sneakingCheckBox.setSelected(nowChosen.isSneaking());

            mapDrawer.drawPointProximity(lastChosen.getPosition());
            mapDrawer.drawCharacters(characters);
        });
    }

    private void nextTurnReset(double miliSeconds){
        for (Character character: characters) {
            if (character.getDoubleVigor() > 20)
                character.setMsLeft(character.getDoubleMsLeft() + miliSeconds);
            else if (character.getDoubleVigor() > 6)
                character.setMsLeft(character.getDoubleMsLeft() + miliSeconds /2);
            else
                character.setMsLeft(character.getDoubleMsLeft() + miliSeconds /3);
            if(character.getDoubleMsLeft() > miliSeconds) {
                character.setVigor(character.getDoubleVigor() + (character.getDoubleMsLeft() - miliSeconds) / 1000 * 2);
                character.setMsLeft(miliSeconds);
            }
            character.setWasDodging(false);
            character.setWasParrying(false);
            character.setWasBouncing(false);
        }
        Effects.refreshTable(charactersTable);
        canvasGameMode();
    }


    public MapDrawer getMapDrawer() {
        return mapDrawer;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void unchoseCharacters(){
        for (Character character: characters) {
            character.setChosen(false);
        }
    }

    public TableView<Character> getCharactersTable() {
        return charactersTable;
    }
}
