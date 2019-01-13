package model.character;

import model.items.ItemsCalculator;
import model.items.armor.*;
import model.items.weapon.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CharacterClass {

    BULLY(new Stats(25, 25, 25, 10, 10, 10, 10, 10, 10),
            createBullyWeaponsMap(), createBullyArmorMaps(), 'S'),
    RASCAL(new Stats(10, 10, 10, 25, 25, 25, 10, 10, 10),
            createRascalWeaponsMap(), createRascalArmorMaps(), 'D'),
    ADEPT(new Stats(10, 10, 10, 10, 10, 10, 25, 25, 25),
            createAdeptWeaponsMap(), createAdeptArmorMaps(), 'M'),
    FANATIC(new Stats(50, 40, 50, 20, 30, 30, 20, 35, 20),
            createFanaticWeaponsMap(), createFanaticArmorMaps(), 'S'),
    WIZARD(new Stats(20, 25, 25, 30, 20, 35, 50, 50, 40),
            createWizardWeaponsMap(), createWizardArmorMaps(), 'M');

    private Stats stats;
    private Map<Weapon, Double> weaponsMap;
    private List<Map<Armor, Double>> armorMaps;
    private char portaitLetter;

    CharacterClass(Stats stats, Map<Weapon, Double> weaponsMap, List<Map<Armor, Double>> armorMaps, char portaitLetter) {
        this.stats = stats;
        this.weaponsMap = weaponsMap;
        this.armorMaps = armorMaps;
        this.portaitLetter = portaitLetter;
    }

    private static Map<Weapon, Double> createBullyWeaponsMap(){
        Map<Weapon, Double> weaponsMap = new HashMap<>();
        weaponsMap.put(Weapon.SHORT_SWORD, 1.);
        weaponsMap.put(Weapon.CHOPPER, .2);
        weaponsMap.put(Weapon.TWO_HAND_SWORD, .5);
        weaponsMap.put(Weapon.ADZE, 1.);
        weaponsMap.put(Weapon.AXE_1H, .2);
        weaponsMap.put(Weapon.AXE_2H, .8);
        weaponsMap.put(Weapon.BIG_ADZE, .6);
        weaponsMap.put(Weapon.CUDGEL, 1.5);
        weaponsMap.put(Weapon.HAMMER, .7);
        weaponsMap.put(Weapon.SPIKE_CLUB, .4);
        weaponsMap.put(Weapon.COMBAT_STICK, .2);
        weaponsMap.put(Weapon.KNIFE, .2);
        weaponsMap.put(Weapon.DAGGER, .1);
        weaponsMap.put(Weapon.KNUCKLE_DUSTER, .5);
        weaponsMap.put(Weapon.SPEAR, .2);
        weaponsMap.put(Weapon.SCYTHE, .3);
        weaponsMap.put(Weapon.LIGHT_CROSSBOW, .1);

        ItemsCalculator.recalcWeaponsMap(weaponsMap);
        return weaponsMap;
    }

    private static List<Map<Armor, Double>> createBullyArmorMaps(){
        List<Map<Armor, Double>> armorMaps =  new ArrayList<>();
        for(int i = 0; i < 6; i++)
            armorMaps.add(new HashMap<>());

        armorMaps.get(0).put(Shield.NOTHING, 1.);
        armorMaps.get(0).put(Shield.WOODEN_SHIELD, .3);
        armorMaps.get(0).put(Shield.BUCKLER, .1);
        armorMaps.get(1).put(BodyArmor.NOTHING, 1.);
        armorMaps.get(1).put(BodyArmor.LEATHER_SHIRT, .7);
        armorMaps.get(1).put(BodyArmor.GAMBISON, .5);
        armorMaps.get(1).put(BodyArmor.LEATHER_ARMOR, .2);
        armorMaps.get(1).put(BodyArmor.STUDDED_ARMOR, .05);
        armorMaps.get(2).put(Helmet.NOTHING, 1.);
        armorMaps.get(2).put(Helmet.LEATHER_HOOD, .5);
        armorMaps.get(2).put(Helmet.CASQUE, .2);
        armorMaps.get(2).put(Helmet.HELMET, .05);
        armorMaps.get(3).put(Gloves.NOTHING, 1.);
        armorMaps.get(3).put(Gloves.RAG_GLOVES, .3);
        armorMaps.get(3).put(Gloves.LEATHER_GLOVES, .1);
        armorMaps.get(4).put(Boots.NOTHING, 1.);
        armorMaps.get(4).put(Boots.RAG_BOOTS, .3);
        armorMaps.get(4).put(Boots.LEATHER_BOOTS, .1);
        armorMaps.get(5).put(Belt.NOTHING, 1.);
        armorMaps.get(5).put(Belt.SASH, .2);
        armorMaps.get(5).put(Belt.LEATHER_BELT, .2);

        for (Map<Armor, Double> armorMap: armorMaps) {
            ItemsCalculator.recalcArmorMap(armorMap);
        }

        return armorMaps;
    }

    private static Map<Weapon, Double> createRascalWeaponsMap(){
        Map<Weapon, Double> weaponsMap = new HashMap<>();
        weaponsMap.put(Weapon.SHORT_SWORD, .8);
        weaponsMap.put(Weapon.SABRE, .6);
        weaponsMap.put(Weapon.TWO_HAND_SWORD, .2);
        weaponsMap.put(Weapon.CLAYMORE, .2);
        weaponsMap.put(Weapon.HALF_HAND_SWORD_2H, .2);
        weaponsMap.put(Weapon.ADZE, .5);
        weaponsMap.put(Weapon.AXE_2H, .1);
        weaponsMap.put(Weapon.PICK, .3);
        weaponsMap.put(Weapon.BIG_ADZE, .1);
        weaponsMap.put(Weapon.CUDGEL, .7);
        weaponsMap.put(Weapon.SPIKE_CLUB, .3);
        weaponsMap.put(Weapon.COMBAT_STICK, .4);
        weaponsMap.put(Weapon.KNIFE, 1.2);
        weaponsMap.put(Weapon.DAGGER, .8);
        weaponsMap.put(Weapon.KNUCKLE_DUSTER, .1);
        weaponsMap.put(Weapon.SPEAR, .5);
        weaponsMap.put(Weapon.SCYTHE, .3);
        weaponsMap.put(Weapon.THROWING_KNIFE_THR, .5);
        weaponsMap.put(Weapon.THROWING_AXE_THR, .3);
        weaponsMap.put(Weapon.SHORT_BOW, 1.);
        weaponsMap.put(Weapon.HUNTER_BOW, .3);
        weaponsMap.put(Weapon.LIGHT_CROSSBOW, .3);

        ItemsCalculator.recalcWeaponsMap(weaponsMap);
        return weaponsMap;
    }

    private static List<Map<Armor, Double>> createRascalArmorMaps(){
        List<Map<Armor, Double>> armorMaps =  new ArrayList<>();
        for(int i = 0; i < 6; i++)
            armorMaps.add(new HashMap<Armor, Double>());

        armorMaps.get(0).put(Shield.NOTHING, 1.);
        armorMaps.get(0).put(Shield.WOODEN_SHIELD, .4);
        armorMaps.get(0).put(Shield.BUCKLER, .2);
        armorMaps.get(1).put(BodyArmor.NOTHING, 1.);
        armorMaps.get(1).put(BodyArmor.LEATHER_SHIRT, 1.);
        armorMaps.get(1).put(BodyArmor.GAMBISON, .8);
        armorMaps.get(1).put(BodyArmor.LEATHER_ARMOR, .3);
        armorMaps.get(1).put(BodyArmor.STUDDED_ARMOR, .1);
        armorMaps.get(2).put(Helmet.NOTHING, 1.);
        armorMaps.get(2).put(Helmet.LEATHER_HOOD, .7);
        armorMaps.get(2).put(Helmet.CASQUE, .3);
        armorMaps.get(2).put(Helmet.HELMET, .1);
        armorMaps.get(3).put(Gloves.NOTHING, 1.);
        armorMaps.get(3).put(Gloves.RAG_GLOVES, .4);
        armorMaps.get(3).put(Gloves.LEATHER_GLOVES, .1);
        armorMaps.get(4).put(Boots.NOTHING, 1.);
        armorMaps.get(4).put(Boots.RAG_BOOTS, .4);
        armorMaps.get(4).put(Boots.LEATHER_BOOTS, .1);
        armorMaps.get(5).put(Belt.NOTHING, 1.);
        armorMaps.get(5).put(Belt.SASH, .2);
        armorMaps.get(5).put(Belt.LEATHER_BELT, .2);

        for (Map<Armor, Double> armorMap: armorMaps) {
            ItemsCalculator.recalcArmorMap(armorMap);
        }

        return armorMaps;
    }

    private static Map<Weapon, Double> createAdeptWeaponsMap(){
        Map<Weapon, Double> weaponsMap = new HashMap<>();
        weaponsMap.put(Weapon.SHORT_SWORD, .3);
        weaponsMap.put(Weapon.TWO_HAND_SWORD, .1);
        weaponsMap.put(Weapon.ADZE, .5);
        weaponsMap.put(Weapon.BIG_ADZE, .1);
        weaponsMap.put(Weapon.CUDGEL, .6);
        weaponsMap.put(Weapon.HAMMER, .3);
        weaponsMap.put(Weapon.SPIKE_CLUB, .1);
        weaponsMap.put(Weapon.KNIFE, .6);
        weaponsMap.put(Weapon.DAGGER, .4);
        weaponsMap.put(Weapon.SPEAR, .2);
        weaponsMap.put(Weapon.SCYTHE, .3);
        weaponsMap.put(Weapon.SHORT_BOW, .3);
        weaponsMap.put(Weapon.WAND, .8);
        weaponsMap.put(Weapon.SPHERE, .8);
        weaponsMap.put(Weapon.STAFF, .8);

        ItemsCalculator.recalcWeaponsMap(weaponsMap);
        return weaponsMap;
    }

    private static List<Map<Armor, Double>> createAdeptArmorMaps(){
        List<Map<Armor, Double>> armorMaps =  new ArrayList<>();
        for(int i = 0; i < 6; i++)
            armorMaps.add(new HashMap<Armor, Double>());

        armorMaps.get(0).put(Shield.NOTHING, 1.);
        armorMaps.get(0).put(Shield.WOODEN_SHIELD, .4);
        armorMaps.get(0).put(Shield.BUCKLER, .2);
        armorMaps.get(1).put(BodyArmor.NOTHING, 1.);
        armorMaps.get(1).put(BodyArmor.LEATHER_SHIRT, 1.);
        armorMaps.get(1).put(BodyArmor.GAMBISON, .8);
        armorMaps.get(1).put(BodyArmor.LEATHER_ARMOR, .3);
        armorMaps.get(1).put(BodyArmor.STUDDED_ARMOR, .1);
        armorMaps.get(2).put(Helmet.NOTHING, 1.);
        armorMaps.get(2).put(Helmet.LEATHER_HOOD, .7);
        armorMaps.get(2).put(Helmet.CASQUE, .3);
        armorMaps.get(2).put(Helmet.HELMET, .1);
        armorMaps.get(3).put(Gloves.NOTHING, 1.);
        armorMaps.get(3).put(Gloves.RAG_GLOVES, .4);
        armorMaps.get(3).put(Gloves.LEATHER_GLOVES, .1);
        armorMaps.get(4).put(Boots.NOTHING, 1.);
        armorMaps.get(4).put(Boots.RAG_BOOTS, .4);
        armorMaps.get(4).put(Boots.LEATHER_BOOTS, .1);
        armorMaps.get(5).put(Belt.NOTHING, 1.);
        armorMaps.get(5).put(Belt.SASH, .2);
        armorMaps.get(5).put(Belt.LEATHER_BELT, .2);

        for (Map<Armor, Double> armorMap: armorMaps) {
            ItemsCalculator.recalcArmorMap(armorMap);
        }

        return armorMaps;
    }

    private static Map<Weapon, Double> createFanaticWeaponsMap(){
        Map<Weapon, Double> weaponsMap = new HashMap<>();
        weaponsMap.put(Weapon.SHORT_SWORD, .5);
        weaponsMap.put(Weapon.SABRE, .4);
        weaponsMap.put(Weapon.CHOPPER, .7);
        weaponsMap.put(Weapon.AXE_2H, .5);
        weaponsMap.put(Weapon.AXE_1H, .5);
        weaponsMap.put(Weapon.TWO_SIDE_AXE, .7);
        weaponsMap.put(Weapon.PICK, .5);
        weaponsMap.put(Weapon.BATTLE_AXE, .6);
        weaponsMap.put(Weapon.HAMMER, .3);
        weaponsMap.put(Weapon.SPIKE_CLUB, .1);
        weaponsMap.put(Weapon.KNIFE, .1);
        weaponsMap.put(Weapon.DAGGER, .5);
        weaponsMap.put(Weapon.KNUCKLE_DUSTER, .2);
        weaponsMap.put(Weapon.SPEAR, .2);
        weaponsMap.put(Weapon.TRIDENT, .3);
        weaponsMap.put(Weapon.SCYTHE, .2);
        weaponsMap.put(Weapon.THROWING_KNIFE_THR, .2);
        weaponsMap.put(Weapon.THROWING_AXE_THR, .2);
        weaponsMap.put(Weapon.LIGHT_CROSSBOW, .4);

        ItemsCalculator.recalcWeaponsMap(weaponsMap);
        return weaponsMap;
    }

    private static List<Map<Armor, Double>> createFanaticArmorMaps(){
        List<Map<Armor, Double>> armorMaps =  new ArrayList<>();
        for(int i = 0; i < 6; i++)
            armorMaps.add(new HashMap<Armor, Double>());

        armorMaps.get(0).put(Shield.NOTHING, 1.);
        armorMaps.get(0).put(Shield.WOODEN_SHIELD, .4);
        armorMaps.get(0).put(Shield.BUCKLER, .2);
        armorMaps.get(1).put(BodyArmor.NOTHING, 1.);
        armorMaps.get(1).put(BodyArmor.LEATHER_SHIRT, 1.);
        armorMaps.get(1).put(BodyArmor.GAMBISON, .8);
        armorMaps.get(1).put(BodyArmor.LEATHER_ARMOR, .3);
        armorMaps.get(1).put(BodyArmor.STUDDED_ARMOR, .1);
        armorMaps.get(2).put(Helmet.NOTHING, 1.);
        armorMaps.get(2).put(Helmet.LEATHER_HOOD, .7);
        armorMaps.get(2).put(Helmet.CASQUE, .3);
        armorMaps.get(2).put(Helmet.HELMET, .1);
        armorMaps.get(3).put(Gloves.NOTHING, 1.);
        armorMaps.get(3).put(Gloves.RAG_GLOVES, .4);
        armorMaps.get(3).put(Gloves.LEATHER_GLOVES, .1);
        armorMaps.get(4).put(Boots.NOTHING, 1.);
        armorMaps.get(4).put(Boots.RAG_BOOTS, .4);
        armorMaps.get(4).put(Boots.LEATHER_BOOTS, .1);
        armorMaps.get(5).put(Belt.NOTHING, 1.);
        armorMaps.get(5).put(Belt.SASH, .2);
        armorMaps.get(5).put(Belt.LEATHER_BELT, .2);

        for (Map<Armor, Double> armorMap: armorMaps) {
            ItemsCalculator.recalcArmorMap(armorMap);
        }

        return armorMaps;
    }

    private static Map<Weapon, Double> createWizardWeaponsMap(){
        Map<Weapon, Double> weaponsMap = new HashMap<>();
        weaponsMap.put(Weapon.SHORT_SWORD, .2);
        weaponsMap.put(Weapon.TWO_HAND_SWORD, .1);
        weaponsMap.put(Weapon.AXE_2H, .1);
        weaponsMap.put(Weapon.PICK, .1);
        weaponsMap.put(Weapon.CUDGEL, .2);
        weaponsMap.put(Weapon.HAMMER, .3);
        weaponsMap.put(Weapon.SPIKE_CLUB, .1);
        weaponsMap.put(Weapon.KNIFE, .6);
        weaponsMap.put(Weapon.DAGGER, .4);
        weaponsMap.put(Weapon.SPEAR, .1);
        weaponsMap.put(Weapon.TRIDENT, .2);
        weaponsMap.put(Weapon.SCYTHE, .2);
        weaponsMap.put(Weapon.SHORT_BOW, .2);
        weaponsMap.put(Weapon.HUNTER_BOW, .2);
        weaponsMap.put(Weapon.WAND, 2.);
        weaponsMap.put(Weapon.SPHERE, 2.);
        weaponsMap.put(Weapon.STAFF, 2.);

        ItemsCalculator.recalcWeaponsMap(weaponsMap);
        return weaponsMap;
    }

    private static List<Map<Armor, Double>> createWizardArmorMaps(){
        List<Map<Armor, Double>> armorMaps =  new ArrayList<>();
        for(int i = 0; i < 6; i++)
            armorMaps.add(new HashMap<Armor, Double>());

        armorMaps.get(0).put(Shield.NOTHING, 1.);
        armorMaps.get(0).put(Shield.WOODEN_SHIELD, .4);
        armorMaps.get(0).put(Shield.BUCKLER, .2);
        armorMaps.get(1).put(BodyArmor.NOTHING, 1.);
        armorMaps.get(1).put(BodyArmor.LEATHER_SHIRT, 1.);
        armorMaps.get(1).put(BodyArmor.GAMBISON, .8);
        armorMaps.get(1).put(BodyArmor.LEATHER_ARMOR, .3);
        armorMaps.get(1).put(BodyArmor.STUDDED_ARMOR, .1);
        armorMaps.get(2).put(Helmet.NOTHING, 1.);
        armorMaps.get(2).put(Helmet.LEATHER_HOOD, .7);
        armorMaps.get(2).put(Helmet.CASQUE, .3);
        armorMaps.get(2).put(Helmet.HELMET, .1);
        armorMaps.get(3).put(Gloves.NOTHING, 1.);
        armorMaps.get(3).put(Gloves.RAG_GLOVES, .4);
        armorMaps.get(3).put(Gloves.LEATHER_GLOVES, .1);
        armorMaps.get(4).put(Boots.NOTHING, 1.);
        armorMaps.get(4).put(Boots.RAG_BOOTS, .4);
        armorMaps.get(4).put(Boots.LEATHER_BOOTS, .1);
        armorMaps.get(5).put(Belt.NOTHING, 1.);
        armorMaps.get(5).put(Belt.SASH, .2);
        armorMaps.get(5).put(Belt.LEATHER_BELT, .2);

        for (Map<Armor, Double> armorMap: armorMaps) {
            ItemsCalculator.recalcArmorMap(armorMap);
        }

        return armorMaps;
    }


    public Stats getStats() {
        return stats;
    }

    public Map<Weapon, Double> getWeaponsMap() {
        return weaponsMap;
    }

    public List<Map<Armor, Double>> getArmorMaps() {
        return armorMaps;
    }

    public char getPortaitLetter() {
        return portaitLetter;
    }
}
