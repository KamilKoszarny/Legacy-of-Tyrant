package model.character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CharacterGroup {

    PREDEFINED(new ArrayList<>(Arrays.asList())),
    INTELLIGENT(new ArrayList<>(Arrays.asList(
            CharacterType.HUMAN, CharacterType.DWARF, CharacterType.ELF, CharacterType.HALFELF))),
    HUMANOIDS(new ArrayList<>(Arrays.asList())),
    ANIMALS(new ArrayList<>(Arrays.asList(CharacterType.BROWN_BEAR, CharacterType.BLACK_BEAR))),
    UNDEADS(new ArrayList<>(Arrays.asList())),
    DEMONS(new ArrayList<>(Arrays.asList()));

    List<CharacterType> belongingTypes;

    CharacterGroup(List<CharacterType> belongingTypes){
        this.belongingTypes = belongingTypes;
    }

    public List<CharacterType> getBelongingTypes() {
        return belongingTypes;
    }
}
