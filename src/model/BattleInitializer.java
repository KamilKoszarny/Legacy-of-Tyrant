package model;

import javafx.scene.paint.Color;
import model.actions.ActionQueuer;
import model.actions.movement.MoveCalculator;
import model.character.*;
import model.character.Character;
import model.items.armor.*;
import model.items.weapon.Weapon;
import model.map.Map;
import model.map.GridGraphCalculator;
import model.map.MapType;
import model.map.heights.MapHeightType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BattleInitializer {

    public static Map initMap(){
        boolean[] roadSides = {true, false, true, true};
        boolean[] riverSides = {false, true, false, true};
        boolean[] waterSides = {false, true, false, false};
        return new Map(50, 50, MapType.VILLAGE, MapHeightType.MOUNTAINS,
                roadSides, riverSides, waterSides, false);
    }

    public static List<Character> initCharacters(Map map){

        Character celion = new Character("Celion", true, Color.YELLOW, CharacterType.HUMAN, CharacterClass.RASCAL,
                new Point(10,10), 0);
        celion.setStats(new Stats(celion, 13, 15, 15, 36, 40, 29, 15, 5, 25));
        celion.getItems().setArmor(new Armor[]{Shield.NOTHING, BodyArmor.LEATHER_ARMOR, Helmet.NOTHING, Gloves.RAG_GLOVES, Boots.NOTHING,
                Belt.NOTHING, Amulet.BRONZE_AMULET, Ring.SILVER_RING, Ring.NOTHING, Shield.BLOCKED});
        celion.getItems().setWeapons(new Weapon[]{Weapon.HUNTER_BOW, Weapon.TWO_HAND_SWORD}, false);
        celion.getStats().setEye(celion.getStats().getEye() - 5);
        StatsCalculator.calcStats(celion);

        Character slimer = new Character("Slimer", true, Color.RED, CharacterType.DWARF, CharacterClass.ADEPT,
                new Point(110,160), 4);
        slimer.setStats(new Stats(slimer, 25, 25, 13, 10, 15, 13, 44, 36, 20));
        slimer.getItems().setArmor(new Armor[]{Shield.NOTHING, BodyArmor.LEATHER_SHIRT, Helmet.CASQUE, Gloves.NOTHING, Boots.RAG_BOOTS,
                Belt.LEATHER_BELT, Amulet.NOTHING, Ring.SILVER_RING, Ring.GOLD_RING, Shield.BLOCKED});
        slimer.getItems().setWeapons(new Weapon[]{Weapon.BIG_ADZE, Weapon.SPEAR}, false);
        StatsCalculator.calcStats(slimer);

        Character skara = new Character("Skara", false, Color.YELLOW, CharacterType.DWARF, CharacterClass.BULLY,
                new Point(10,20), 0);
        skara.setStats(new Stats(skara, 39, 35, 35, 10, 15, 5, 10, 20, 10));
        skara.getItems().setArmor(new Armor[]{Shield.BUCKLER, BodyArmor.GAMBISON, Helmet.CASQUE, Gloves.RAG_GLOVES, Boots.LEATHER_BOOTS,
                Belt.NOTHING, Amulet.NOTHING, Ring.NOTHING, Ring.NOTHING, Shield.WOODEN_SHIELD});
        skara.getItems().setWeapons(new Weapon[]{Weapon.CHOPPER, Weapon.SPIKE_CLUB}, false);
        StatsCalculator.calcStats(skara);

        Character irith = new Character("Irith", false, Color.RED, CharacterType.ELF, CharacterClass.RASCAL,
                new Point(120,180), 4);
        irith.setStats(new Stats(irith, 11, 16, 24, 40, 31, 38, 15, 12, 13));
        irith.getItems().setArmor(new Armor[]{Shield.NOTHING, BodyArmor.NOTHING, Helmet.LEATHER_HOOD, Gloves.LEATHER_GLOVES, Boots.NOTHING,
                Belt.NOTHING, Amulet.NOTHING, Ring.NOTHING, Ring.NOTHING, Shield.NOTHING});
        irith.getItems().setWeapons(new Weapon[]{Weapon.SHORT_BOW, Weapon.DAGGER}, false);
        StatsCalculator.calcStats(irith);

        ArrayList<Character> characters = new ArrayList<>(Arrays.asList(celion, slimer, skara, irith));
//        ArrayList<Character> characters = new ArrayList<>(new ArrayList<>(Arrays.asList(celion)));

        MoveCalculator.pushCharsToClosestWalkable(map, characters);
        GridGraphCalculator.regenerateGridGraph(map, map.getPoints().keySet(), characters);
        ActionQueuer.initCharacterQueues(characters);

        return characters;
    }


}
