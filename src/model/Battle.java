package model;

import javafx.scene.paint.Color;
import model.armor.*;
import model.character.Character;
import model.character.CharacterClass;
import model.character.CharacterType;
import model.map.*;
import model.map.heights.MapHeightType;
import model.weapon.Weapon;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Battle {

    Map map;
    List<Character> characters;

    public Battle() {
        initMap();
        initCharacters();
    }

    private void initMap(){
        boolean[] roadSides = {false, true, true, true};
        map = new Map(50, 50, MapType.FOREST, MapHeightType.PEAK, roadSides);
    }

    private void initCharacters(){
        characters = new ArrayList<>();
        Character czlehulec = new Character("Człehulec", Color.GRAY, CharacterType.HUMAN, CharacterClass.RASCAL, new Point(10,10));
        czlehulec.setCurrentPA(13, 15, 15, 36, 40, 29, 15, 5, 25);
        czlehulec.setWeapons(new Weapon[]{Weapon.HUNTER_BOW, Weapon.TWO_HAND_SWORD});
        czlehulec.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.LEATHER_ARMOR, Helmet.NOTHING, Gloves.RAG_GLOVES, Boots.NOTHING, Belt.NOTHING});
        czlehulec.setEye(czlehulec.getDoubleEye() - 5);
        StatsCalculator.calcCharSA(czlehulec, false);

        Character slimako = new Character("Slimako", Color.BLACK, CharacterType.DWARF, CharacterClass.ADEPT, new Point(20,10));
        slimako.setCurrentPA(25, 25, 13, 10, 15, 13, 44, 36, 20);
        slimako.setWeapons(new Weapon[]{Weapon.BIG_ADZE, Weapon.SPEAR});
        slimako.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.NOTHING, Helmet.CASQUE, Gloves.NOTHING, Boots.NOTHING, Belt.LEATHER_BELT});
        StatsCalculator.calcCharSA(slimako, false);

        Character skowronka = new Character("Skowronka", Color.RED, CharacterType.DWARF, CharacterClass.BULLY, new Point(10,20));
        skowronka.setCurrentPA(39, 35, 25, 10, 15, 5, 10, 20, 10);
        skowronka.setWeapons(new Weapon[]{Weapon.CHOPPER, Weapon.SPIKE_CLUB});
        skowronka.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.GAMBISON, Helmet.NOTHING, Gloves.NOTHING, Boots.NOTHING, Belt.NOTHING});
        StatsCalculator.calcCharSA(skowronka, false);

        Character irith = new Character("Irith", Color.BLUE, CharacterType.ELF, CharacterClass.RASCAL, new Point(150,150));
        irith.setCurrentPA(11, 16, 24, 40, 31, 38, 15, 12, 13);
        irith.setWeapons(new Weapon[]{Weapon.SHORT_BOW, Weapon.DAGGER});
        irith.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.NOTHING, Helmet.NOTHING, Gloves.NOTHING, Boots.NOTHING, Belt.NOTHING});
        StatsCalculator.calcCharSA(irith, false);

        characters.addAll(new ArrayList<>(Arrays.asList(czlehulec, slimako, skowronka, irith)));

    }

    public Map getMap() {
        return map;
    }

    public List<Character> getCharacters() {
        return characters;
    }
}