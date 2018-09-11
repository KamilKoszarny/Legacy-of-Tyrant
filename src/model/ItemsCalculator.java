package model;

import model.character.Character;
import model.character.CharacterGroup;
import model.map.Terrain;
import model.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;
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
}
