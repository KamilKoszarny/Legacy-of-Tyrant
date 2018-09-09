package model.character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CharacterGroup {

    PREDEFINED(null),
    INTELLIGENT(new ArrayList<>(Arrays.asList(
            CharacterType.HUMAN, CharacterType.DWARF, CharacterType.ELF, CharacterType.HALFELF))),
    HUMANOIDS(null),
    ANIMALS(null),
    UNDEADS(null),
    DEMONS(null);

    List<CharacterType> belongingTypes;

    CharacterGroup(List<CharacterType> belongingTypes){
        this.belongingTypes = belongingTypes;
    }

    public List<CharacterType> getBelongingTypes() {
        return belongingTypes;
    }
}
