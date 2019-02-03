package model;

import javafx.scene.paint.Color;
import model.actions.ActionQueuer;
import model.character.*;
import model.actions.movement.CharMover;
import model.character.Character;
import model.items.armor.*;
import model.items.weapon.Weapon;
import model.map.Map;
import model.map.GridGrapCalculator;
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
        return new Map(50, 50, MapType.VILLAGE, MapHeightType.PEAK,
                roadSides, riverSides, waterSides, true);
    }

    public static List<Character> initCharacters(Map map){

        Character czlehulec = new Character("Czlehulec", true, Color.YELLOW, CharacterType.HUMAN, CharacterClass.RASCAL,
                new Point(10,10), 1);
        czlehulec.setStats(new Stats(czlehulec, 13, 15, 15, 36, 40, 29, 15, 5, 25));
        czlehulec.getItems().setArmor(new Armor[]{Shield.NOTHING, BodyArmor.LEATHER_ARMOR, Helmet.NOTHING, Gloves.RAG_GLOVES, Boots.NOTHING,
                Belt.NOTHING, Amulet.BRONZE_AMULET, Ring.SILVER_RING, Ring.NOTHING, Shield.BLOCKED});
        czlehulec.getItems().setWeapons(new Weapon[]{Weapon.HUNTER_BOW, Weapon.TWO_HAND_SWORD}, false);
        czlehulec.getStats().setEye(czlehulec.getStats().getEye() - 5);
        StatsCalculator.calcStats(czlehulec);

        Character slimako = new Character("Slimako", true, Color.RED, CharacterType.DWARF, CharacterClass.ADEPT,
                new Point(20,10), 2);
        slimako.setStats(new Stats(slimako, 25, 25, 13, 10, 15, 13, 44, 36, 20));
        slimako.getItems().setArmor(new Armor[]{Shield.NOTHING, BodyArmor.LEATHER_SHIRT, Helmet.CASQUE, Gloves.NOTHING, Boots.RAG_BOOTS,
                Belt.LEATHER_BELT, Amulet.NOTHING, Ring.SILVER_RING, Ring.GOLD_RING, Shield.BLOCKED});
        slimako.getItems().setWeapons(new Weapon[]{Weapon.BIG_ADZE, Weapon.SPEAR}, false);
        StatsCalculator.calcStats(slimako);

        Character skowronka = new Character("Skowronka", false, Color.YELLOW, CharacterType.DWARF, CharacterClass.BULLY,
                new Point(10,20), 6);
        skowronka.setStats(new Stats(skowronka, 39, 35, 35, 10, 15, 5, 10, 20, 10));
        skowronka.getItems().setArmor(new Armor[]{Shield.BUCKLER, BodyArmor.GAMBISON, Helmet.CASQUE, Gloves.RAG_GLOVES, Boots.LEATHER_BOOTS,
                Belt.NOTHING, Amulet.NOTHING, Ring.NOTHING, Ring.NOTHING, Shield.WOODEN_SHIELD});
        skowronka.getItems().setWeapons(new Weapon[]{Weapon.CHOPPER, Weapon.SPIKE_CLUB}, false);
        StatsCalculator.calcStats(skowronka);

        Character irith = new Character("Irith", false, Color.RED, CharacterType.ELF, CharacterClass.RASCAL,
                new Point(20,20), 7);
        irith.setStats(new Stats(irith, 11, 16, 24, 40, 31, 38, 15, 12, 13));
        irith.getItems().setArmor(new Armor[]{Shield.NOTHING, BodyArmor.NOTHING, Helmet.LEATHER_HOOD, Gloves.LEATHER_GLOVES, Boots.NOTHING,
                Belt.NOTHING, Amulet.NOTHING, Ring.NOTHING, Ring.NOTHING, Shield.NOTHING});
        irith.getItems().setWeapons(new Weapon[]{Weapon.SHORT_BOW, Weapon.DAGGER}, false);
        StatsCalculator.calcStats(irith);

        ArrayList<Character> characters = new ArrayList<>(Arrays.asList(czlehulec, slimako, skowronka, irith));
//        ArrayList<Character> characters = new ArrayList<>(new ArrayList<>(Arrays.asList(czlehulec)));

        CharMover.pushCharsToClosestWalkable(map, characters);
        GridGrapCalculator.regenerateGridGraph(map, map.getPoints().keySet(), characters);
        ActionQueuer.initCharacterQueues(characters);

        return characters;
    }


}
