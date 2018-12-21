package model;

import javafx.scene.paint.Color;
import model.character.Character;
import model.character.CharacterClass;
import model.character.CharacterType;
import model.character.StatsCalculator;
import model.actions.movement.CharMover;
import model.items.armor.*;
import model.items.weapon.Weapon;
import model.map.Map;
import model.map.MapGridCalc;
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
        return new Map(50, 50, MapType.VILLAGE, MapHeightType.PEAK, roadSides, riverSides, waterSides);
    }

    public static List<Character> initCharacters(Map map){
        ArrayList<Character> characters = new ArrayList<>();

        Character czlehulec = new Character("Czlehulec", true, Color.YELLOW, CharacterType.HUMAN, CharacterClass.RASCAL,
                new Point(10,10), 1);
        czlehulec.setCurrentPA(13, 15, 15, 36, 40, 29, 15, 5, 25);
        czlehulec.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.LEATHER_ARMOR, Helmet.NOTHING, Gloves.RAG_GLOVES, Boots.NOTHING,
                Belt.NOTHING, Amulet.BRONZE_AMULET, Ring.SILVER_RING, Ring.NOTHING, Shield.BLOCKED});
        czlehulec.setWeapons(new Weapon[]{Weapon.HUNTER_BOW, Weapon.TWO_HAND_SWORD});
        czlehulec.setEye(czlehulec.getEye() - 5);
        StatsCalculator.calcCharSA(czlehulec);

        Character slimako = new Character("Slimako", true, Color.YELLOW, CharacterType.DWARF, CharacterClass.ADEPT,
                new Point(20,10), 2);
        slimako.setCurrentPA(25, 25, 13, 10, 15, 13, 44, 36, 20);
        slimako.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.NOTHING, Helmet.CASQUE, Gloves.NOTHING, Boots.RAG_BOOTS,
                Belt.LEATHER_BELT, Amulet.NOTHING, Ring.SILVER_RING, Ring.GOLD_RING, Shield.BLOCKED});
        slimako.setWeapons(new Weapon[]{Weapon.BIG_ADZE, Weapon.SPEAR});
        StatsCalculator.calcCharSA(slimako);

        Character skowronka = new Character("Skowronka", false, Color.YELLOW, CharacterType.DWARF, CharacterClass.BULLY,
                new Point(10,20), 6);
        skowronka.setCurrentPA(9, 5, 5, 10, 15, 5, 10, 20, 10);
        skowronka.setArmor(new Armor[]{Shield.BUCKLER, BodyArmor.GAMBISON, Helmet.NOTHING, Gloves.NOTHING, Boots.LEATHER_BOOTS,
                Belt.NOTHING, Amulet.NOTHING, Ring.NOTHING, Ring.NOTHING, Shield.WOODEN_SHIELD});
        skowronka.setWeapons(new Weapon[]{Weapon.CHOPPER, Weapon.SPIKE_CLUB});
        StatsCalculator.calcCharSA(skowronka);

        Character irith = new Character("Irith", false, Color.YELLOW, CharacterType.ELF, CharacterClass.RASCAL,
                new Point(20,20), 7);
        irith.setCurrentPA(11, 16, 24, 40, 31, 38, 15, 12, 13);
        irith.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.NOTHING, Helmet.LEATHER_HOOD, Gloves.LEATHER_GLOVES, Boots.NOTHING,
                Belt.NOTHING, Amulet.NOTHING, Ring.NOTHING, Ring.NOTHING, Shield.NOTHING});
        irith.setWeapons(new Weapon[]{Weapon.SHORT_BOW, Weapon.DAGGER});
        StatsCalculator.calcCharSA(irith);

        characters.addAll(new ArrayList<>(Arrays.asList(czlehulec, slimako, skowronka, irith)));

        CharMover.setCharacters(characters);
        CharMover.pushCharsToClosestWalkable(map);
        MapGridCalc.regenerateGridGraph(map, map.getPoints().keySet(), characters);

        return characters;
    }


}
