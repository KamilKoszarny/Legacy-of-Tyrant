package model.items.weapon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum WeaponGroup {
    SWORDS(new ArrayList<>(Arrays.asList(
            Weapon.SHORT_SWORD, Weapon.SABRE, Weapon.CHOPPER, Weapon.TWO_HAND_SWORD, Weapon.HALF_HAND_SWORD_1H, Weapon.HALF_HAND_SWORD_2H))),
    AXES(new ArrayList<>(Arrays.asList(
            Weapon.ADZE, Weapon.AXE_1H, Weapon.AXE_2H, Weapon.TWO_SIDE_AXE, Weapon.PICK, Weapon.BIG_ADZE, Weapon.BATTLE_AXE))),
    MACES(new ArrayList<>(Arrays.asList(
            Weapon.CUDGEL, Weapon.HAMMER, Weapon.SPIKE_CLUB, Weapon.COMBAT_STICK))),
    SHORT(new ArrayList<>(Arrays.asList(
            Weapon.KNIFE, Weapon.DAGGER, Weapon.CHOPPER, Weapon.KNUCKLE_DUSTER, Weapon.THROWING_KNIFE_DIR, Weapon.THROWING_AXE_DIR))),
    LONG(new ArrayList<>(Arrays.asList(
            Weapon.SPEAR, Weapon.TRIDENT, Weapon.PIQUE, Weapon.HARPOON, Weapon.SCYTHE, Weapon.JAVELIN_DIR))),
    THROWING(new ArrayList<>(Arrays.asList(
            Weapon.THROWING_KNIFE_THR, Weapon.THROWING_AXE_THR, Weapon.JAVELIN_THR))),
    RANGE(new ArrayList<>(Arrays.asList(
            Weapon.SHORT_BOW, Weapon.HUNTER_BOW, Weapon.LONG_BOW, Weapon.LIGHT_CROSSBOW, Weapon.CROSSBOW))),
    MAGES(new ArrayList<>(Arrays.asList(
            Weapon.WAND, Weapon.SPHERE, Weapon.STAFF)));

    private List<Weapon> weapons;

    WeaponGroup(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }
}
