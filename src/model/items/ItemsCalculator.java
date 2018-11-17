package model.items;

import model.items.armor.*;
import model.character.Character;
import model.character.CharacterGroup;
import model.items.weapon.Weapon;

import java.util.Map;
import java.util.Random;

public class ItemsCalculator {

    public static Weapon[] shuffleWeapons(Character character){
        Weapon[] weapons = {Weapon.NOTHING, Weapon.NOTHING};

        if(CharacterGroup.INTELLIGENT.getBelongingTypes().contains(character.getType()) ||
                CharacterGroup.HUMANOIDS.getBelongingTypes().contains(character.getType()) ) {
            Map<Weapon, Double> weaponsMap = character.getCharClass().getWeaponsMap();

            Random r = new Random();
            double n1 = r.nextDouble();
            double n2 = r.nextDouble();
            double wp = 0;

            double weaponsCount = r.nextInt(2) + 1;
            if (weaponsCount == 1)
                n2 = 100;


            for (Weapon weapon : weaponsMap.keySet()) {
                wp += weaponsMap.get(weapon);
                if (n1 < wp) {
                    weapons[0] = weapon;
                    n1 = 100;
                }
                if (n2 < wp) {
                    weapons[1] = weapon;
                    n2 = 100;
                }
            }
        }

        return weapons;
    }

    public static Armor[] shuffleArmor(Character character){
        Armor[] armors = {Shield. NOTHING, BodyArmor.NOTHING, Helmet.NOTHING, Gloves.NOTHING, Boots.NOTHING, Belt.NOTHING};

        if(CharacterGroup.INTELLIGENT.getBelongingTypes().contains(character.getType()) ||
                CharacterGroup.HUMANOIDS.getBelongingTypes().contains(character.getType()) ) {
            int i = 0;
            for (Map<Armor, Double> armorMap: character.getCharClass().getArmorMaps()) {
                Random r = new Random();
                double n = r.nextDouble();
                double ap = 0;

                for (Armor armor : armorMap.keySet()) {
                    ap += armorMap.get(armor);
                    if (n < ap) {
                        armors[i] = armor;
                        n += 100;
                    }
                }
                i++;
            }
        }

        if(character.getWeapon().getHands() == 2)
            armors[0] = Shield.NOTHING;

        return armors;
    }

    public static Map<Weapon, Double> recalcWeaponsMap(Map<Weapon, Double> weaponsMap) {
        double sum = 0;
        for (Double probability: weaponsMap.values()) {
            sum += probability;
        }
        for (Weapon weapon: weaponsMap.keySet()) {
            weaponsMap.put(weapon, weaponsMap.get(weapon) / sum);
        }

        return weaponsMap;
    }

    public static Map<Armor, Double> recalcArmorMap(Map<Armor, Double> armorMap) {
        double sum = 0;
        for (Double probability: armorMap.values()) {
            sum += probability;
        }
        for (Armor armor: armorMap.keySet()) {
            armorMap.put(armor, armorMap.get(armor) / sum);
        }

        return armorMap;
    }


}
