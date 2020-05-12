package viewIso.panel;

import controller.isoView.isoPanel.Panel;
import controller.isoView.isoPanel.ItemClickController;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Battle;
import model.character.Character;
import model.items.Item;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CharPanelViewer {

    private static Panel panel;
    private static List<Character> characters;
    private static Rectangle inventoryRect;

    public CharPanelViewer(Panel panel, List<Character> characters) {
        CharPanelViewer.panel = panel;
        CharPanelViewer.characters = characters;
        initInvRect();
    }

    public static void refresh() {
        List<Character> chosenCharacters = calcChosenCharacters();
        if (chosenCharacters.size() == 1) {
            Character firstChosenCharacter = chosenCharacters.get(0);
            refreshLabels(firstChosenCharacter);
            refreshBars(firstChosenCharacter);
            refreshPortrait(firstChosenCharacter);
            refreshEquipment(firstChosenCharacter);
            refreshCharInventory(firstChosenCharacter.getItems().getInventory());
        }
        NextTurnButtonViewer.refreshNextTurnButtonLook();
    }

    private static void initInvRect() {
        Rectangle invFirstRect = ItemClickController.calcInventoryScreenRect(panel.getInventoryRectangle(), new int[]{0, 0});
        inventoryRect = InventoryRectanglesViewer.createInventoryRectangle(new Point((int)invFirstRect.getX(), (int)invFirstRect.getY()));
        InventoryRectanglesViewer.drawInventoryRectangle(inventoryRect);
    }

    private static void refreshLabels(Character character){
        for (Label label: panel.getCharStatsLabels()) {
            String parameterText = getParameterText(label, character);
            label.setText(parameterText);
        }
    }

    //use label Id
    private static String getParameterText(Label label, Character character) {
        String parameter = label.getId().substring(0, label.getId().length() - 5);

        String parText = CharParameter.signByName(parameter) + findValue(character, parameter);

        if (CharParameter.isWithCurrent(parameter)){
            parameter += "Max";
            parText += "/" + findValue(character, parameter);
        }

        return parText;
    }

    private static String findValue(Character character, String parameter) {
        if (parameter.equals("name"))
            return character.getName();
        if (parameter.equals("type"))
            return character.getType().toString();
        if (parameter.equals("charClass"))
            return character.getCharClass().toString();

        String value = "";
        String getterName = "get" + parameter.substring(0, 1).toUpperCase() + parameter.substring(1);
        java.lang.reflect.Method method;
        Class returnType = null;
        try {
            method = character.getStats().getClass().getMethod(getterName);
            returnType = method.getReturnType();
            value = method.invoke(character.getStats()).toString();
        } catch (SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {}

        if (returnType != null && returnType.equals(Double.TYPE)) {
            Double doubleValue = Double.parseDouble(value);
            doubleValue = (double) Math.round(doubleValue * 10.) / 10;
            value = doubleValue.toString();
        } else if (returnType != null && returnType.equals(Float.TYPE)) {
            Float floatValue = Float.parseFloat(value);
            Integer intValue = (int) Math.round(floatValue);
            value = intValue.toString();
        }
        return value;
    }

    private static void refreshBars(Character character) {
        double baseWidth = panel.getCharBars().get(0).getPrefWidth();
        ProgressBar hpBar = panel.getCharBars().get(0), manaBar = panel.getCharBars().get(1), vigorBar = panel.getCharBars().get(2);

        hpBar.setMinWidth(baseWidth * character.getStats().getHitPointsMax() / 100.);
        hpBar.setMaxWidth(baseWidth * character.getStats().getHitPointsMax() / 100.);
        hpBar.setTranslateY(baseWidth / 2 - hpBar.getMinWidth() / 2);
        hpBar.setProgress((double)character.getStats().getHitPoints() / (double)character.getStats().getHitPointsMax());
        manaBar.setMinWidth(baseWidth * character.getStats().getManaMax() * 5 / 100.);
        manaBar.setMaxWidth(baseWidth * character.getStats().getManaMax() * 5 / 100.);
        manaBar.setTranslateY(baseWidth / 2 - manaBar.getMinWidth() / 2);
        manaBar.setProgress((double)character.getStats().getMana() / (double)character.getStats().getManaMax());
        vigorBar.setMinWidth(baseWidth * character.getStats().getVigorMax() / 100.);
        vigorBar.setMaxWidth(baseWidth * character.getStats().getVigorMax() / 100.);
        vigorBar.setTranslateY(baseWidth / 2 - vigorBar.getMinWidth() / 2);
        vigorBar.setProgress((double)character.getStats().getVigor() / (double)character.getStats().getVigorMax());
    }

    private static void refreshPortrait(Character character) {
        panel.getCharPortraitBackgroundRect().setFill(character.getColor());
        panel.getPortraitRect().setFill(new ImagePattern(character.getPortrait()));
    }

    private static void refreshEquipment(Character character) {
        refreshEqRect(panel.getWeaponRect(), character.getItems().getWeapon());
        refreshEqRect(panel.getSpareWeaponRect(), character.getItems().getSpareWeapon());
        refreshEqRect(panel.getHelmetRect(), character.getItems().getHelmet());
        refreshEqRect(panel.getArmorRect(), character.getItems().getBodyArmorItem());
        refreshEqRect(panel.getShieldRect(), character.getItems().getShield());
        refreshEqRect(panel.getGlovesRect(), character.getItems().getGloves());
        refreshEqRect(panel.getBootsRect(), character.getItems().getBoots());
        refreshEqRect(panel.getBeltRect(), character.getItems().getBelt());
        refreshEqRect(panel.getAmuletRect(), character.getItems().getAmulet());
        refreshEqRect(panel.getRing1Rect(), character.getItems().getRing1());
        refreshEqRect(panel.getRing2Rect(), character.getItems().getRing2());
        refreshEqRect(panel.getSpareShieldRect(), character.getItems().getSpareShield());
    }

    private static void refreshEqRect(Rectangle rectangle, Item item) {
        Image image = item.getImage();
        rectangle.setWidth(image.getWidth());
        rectangle.setHeight(image.getHeight());
        rectangle.setFill(new ImagePattern(image));

        String name = item.getName();
        Tooltip tooltip = new Tooltip(name);
        Tooltip.install(rectangle, tooltip);
    }

    public static void refreshCharInventory(Map<Item, int[]> inventory) {
        InventoryRectanglesViewer.refreshInventory(inventory, inventoryRect);
    }


    private static List<Character> calcChosenCharacters(){
        List<Character> chosenCharacters = new ArrayList<>();
        for (Character character: characters) {
            if (character.equals(Battle.getChosenCharacter())) {
                chosenCharacters.add(character);
            }
        }
        return chosenCharacters;
    }

    public static Rectangle getInventoryRect() {
        return inventoryRect;
    }

    public static Panel getPanel() {
        return panel;
    }
}
