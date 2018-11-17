package viewIso.panel;

public enum CharParameter {
    NAME("name", "NAME: ", "", ""),
    TYPE("type", "TYPE: ", "", ""),
    CHAR_CLASS("charClass", "CLASS: ", "", ""),
    HP("hitPoints", "", "", "", true),
    MANA("mana", "", "", "", true),
    VIGOR("vigor", "", "", "", true),
    V("vim", "V: ", "", ""),
    STR("strength", "STR: ", "", ""),
    DUR("durability", "DUR: ", "", ""),
    STA("stamina", "STA: ", "", ""),
    D("dexterity", "D: ", "", ""),
    ARM("arm", "ARM: ", "", ""),
    EYE("eye", "EYE: ", "", ""),
    AGI("agility", "AGI: ", "", ""),
    I("intelligence", "I: ", "", ""),
    KNO("knowledge", "KNO: ", "", ""),
    FOC("focus", "FOC: ", "", ""),
    SPI("spirit", "SPI: ", "", ""),
    LOAD("load", "load: ", "", ""),
    SPEED("speed", "mSpeed: ", "", ""),
    ATT_SPEED("attackSpeed", "aSpeed: ", "", ""),
    RANGE("range", "range: ", "", ""),
    BLOCK("block", "block: ", "", ""),
    MAG_RES("magicResistance", "mRes: ", "", ""),
    DMG_MIN("dmgMin", "dmg\u2193: ", "", ""),
    DMG_MAX("dmgMax", "dmg\u2191: ", "", ""),
    ACCURACY("accuracy", "acc: ", "", ""),
    AVOIDANCE("avoidance", "avo: ", "", ""),
    ;

    private String name, sign, shortDescription, longDescription;
    private boolean withCurrent = false;

    CharParameter(String name, String sign, String shortDescription, String longDescription) {
        this.name = name;
        this.sign = sign;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }

    CharParameter(String name, String sign, String shortDescription, String longDescription, boolean withCurrent) {
        this.name = name;
        this.sign = sign;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.withCurrent = withCurrent;
    }

    public static String signByName(String name) {
        for (CharParameter charParameter: CharParameter.values()) {
            if (charParameter.name.equals(name))
                return charParameter.sign;
        }
        return null;
    }

    public static boolean isWithCurrent(String name) {
        for (CharParameter charParameter: CharParameter.values()) {
            if (charParameter.name.equals(name))
                return charParameter.withCurrent;
        }
        return false;
    }
}
