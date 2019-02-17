package model.ai;

import helpers.my.RandomHelper;
import javafx.scene.paint.Color;
import model.Battle;
import model.character.Character;
import model.map.lights.VisibilityCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnemyChooser {

    static Character chooseVisibleEnemy(Character aiCharacter) {
        return (Character) RandomHelper.shuffleFrom2DoubleMap(calcEnemyProbabilityMap(aiCharacter));
    }

    private static Map<Character, Double> calcEnemyProbabilityMap(Character aiCharacter) {
        Map<Character, Double> enemyProbabilityMap = new HashMap<>();
        List<Character> aiTeamVisibleEnemies = aiTeamVisibleEnemies(aiCharacter.getColor());
        for (Character enemy: aiTeamVisibleEnemies) {
            enemyProbabilityMap.put(enemy, aiCharacter.getPosition().distance(enemy.getPosition()));
        }
        return enemyProbabilityMap;
    }

    private static List<Character> aiTeamVisibleEnemies(Color aiColor) {
        List<Character> aiTeamVisibleEnemies = new ArrayList<>();
        for (Character character: Battle.getPlayerAliveCharacters()) {
            if (VisibilityCalculator.isInTeamView(character.getPosition(), aiColor)) {
                aiTeamVisibleEnemies.add(character);
            }
        }
        return aiTeamVisibleEnemies;
    }

}
