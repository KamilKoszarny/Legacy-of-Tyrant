package model.items;

import model.items.armor.*;
import model.items.weapon.Weapon;

public class ItemsCalculator {



    public static Item getNothingItemByNo(int no) {
        switch (no) {
            case 0: return Weapon.NOTHING;
            case 1: return Weapon.NOTHING;
            case 2: return Shield.NOTHING;
            case 3: return BodyArmor.NOTHING;
            case 4: return Helmet.NOTHING;
            case 5: return Gloves.NOTHING;
            case 6: return Boots.NOTHING;
            case 7: return Belt.NOTHING;
            case 8: return Amulet.NOTHING;
            case 9: return Ring.NOTHING;
            case 10: return Ring.NOTHING;
            case 11: return Shield.NOTHING;
        }
        return null;
    }
}
