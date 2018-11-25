package viewIso.panel;

import controller.isoView.isoMap.IsoMapClickController;
import controller.isoView.isoPanel.Panel;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.actions.ItemHandler;
import model.character.Character;
import model.items.Item;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CharDescriptor {

    private Panel panel;
    private List<Character> characters;
    private Rectangle inventoryRect, inventoryClickRect;
    private boolean inventoryRectsSet = false;

    public CharDescriptor(Panel panel, List<Character> characters) {
        this.panel = panel;
        this.characters = characters;
        initInvRect();
    }

    private void initInvRect() {
        Image inventoryImg = new Image("/items/inventory.png");
        Rectangle invFirstRect = IsoMapClickController.calcInventoryScreenRect(panel, new int[]{0, 0});
        inventoryRect = new Rectangle(invFirstRect.getX(), invFirstRect.getY(),
                ItemHandler.INVENTORY_X * ItemHandler.ITEM_SLOT_SIZE, ItemHandler.INVENTORY_Y * ItemHandler.ITEM_SLOT_SIZE);
        inventoryRect.setFill(new ImagePattern(inventoryImg));
        Pane pane = (Pane) panel.getCatchedItemRect().getParent();
        inventoryRect.setVisible(true);
        pane.getChildren().add(inventoryRect);
    }

    public void refresh() {
        List<Character> chosenCharacters = calcChosenCharacters();
        if (chosenCharacters.size() == 1) {
            Character firstChosenCharacter = chosenCharacters.get(0);
            refreshLabels(firstChosenCharacter);
            refreshBars(firstChosenCharacter);
            refreshPortrait(firstChosenCharacter);
            refreshEquipment(firstChosenCharacter);
            refreshInventory(firstChosenCharacter);
        }
    }

    private void refreshLabels(Character character){
        for (Label label: panel.getCharLabels()) {
            String parameterText = getParameterText(label, character);
            label.setText(parameterText);
        }
    }

    private String getParameterText(Label label, Character character) {
        String parameter = label.getId().substring(0,label.getId().length() - 5);

        String parText = CharParameter.signByName(parameter) + findValue(character, parameter);

        if (CharParameter.isWithCurrent(parameter)){
            parameter = "current" + parameter.substring(0, 1).toUpperCase() + parameter.substring(1, parameter.length());
            parText = findValue(character, parameter) + "/" + parText;
        }

        return parText;
    }

    private String findValue(Character character, String parameter) {
        String value = "";
        String getterName = "get" + parameter.substring(0, 1).toUpperCase() + parameter.substring(1, parameter.length());
        java.lang.reflect.Method method;
        Class returnType = null;
        try {
            method = character.getClass().getMethod(getterName);
            returnType = method.getReturnType();
            value = method.invoke(character).toString();
        } catch (SecurityException | NoSuchMethodException e) {} catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if (returnType != null && returnType.equals(Double.TYPE)) {
            Double doubleValue = Double.parseDouble(value);
            doubleValue = (double) Math.round(doubleValue * 10.) / 10;
            value = doubleValue.toString();
        }
        return value;
    }

    private void refreshBars(Character character) {
        double baseWidth = panel.getCharBars().get(0).getPrefWidth();
        ProgressBar hpBar = panel.getCharBars().get(0), manaBar = panel.getCharBars().get(1), vigorBar = panel.getCharBars().get(2);

        hpBar.setMinWidth(baseWidth * character.getHitPoints() / 100.);
        hpBar.setMaxWidth(baseWidth * character.getHitPoints() / 100.);
        hpBar.setTranslateY(baseWidth / 2 - hpBar.getMinWidth() / 2);
        hpBar.setProgress((double)character.getCurrentHitPoints() / (double)character.getHitPoints());
        manaBar.setMinWidth(baseWidth * character.getMana() * 5 / 100.);
        manaBar.setMaxWidth(baseWidth * character.getMana() * 5 / 100.);
        manaBar.setTranslateY(baseWidth / 2 - manaBar.getMinWidth() / 2);
        manaBar.setProgress((double)character.getCurrentMana() / (double)character.getMana());
        vigorBar.setMinWidth(baseWidth * character.getVigor() / 100.);
        vigorBar.setMaxWidth(baseWidth * character.getVigor() / 100.);
        vigorBar.setTranslateY(baseWidth / 2 - vigorBar.getMinWidth() / 2);
        vigorBar.setProgress((double)character.getCurrentVigor() / (double)character.getVigor());
    }

    private void refreshPortrait(Character character) {
        panel.getCharPortraitBackgroundRect().setFill(character.getColor());
        panel.getPortraitRect().setFill(new ImagePattern(character.getPortrait()));
    }

    private void refreshEquipment(Character character) {
        refreshEqRect(character, panel.getWeaponRect(), character.getWeapon().getImage());
        refreshEqRect(character, panel.getSpareWeaponRect(), character.getSpareWeapon().getImage());
        refreshEqRect(character, panel.getHelmetRect(), character.getHelmet().getImage());
        refreshEqRect(character, panel.getArmorRect(), character.getBodyArmorItem().getImage());
        refreshEqRect(character, panel.getShieldRect(), character.getShield().getImage());
        refreshEqRect(character, panel.getGlovesRect(), character.getGloves().getImage());
        refreshEqRect(character, panel.getBootsRect(), character.getBoots().getImage());
        refreshEqRect(character, panel.getBeltRect(), character.getBelt().getImage());
        refreshEqRect(character, panel.getAmuletRect(), character.getAmulet().getImage());
        refreshEqRect(character, panel.getRing1Rect(), character.getRing1().getImage());
        refreshEqRect(character, panel.getRing2Rect(), character.getRing2().getImage());
        refreshEqRect(character, panel.getSpareShieldRect(), character.getSpareShield().getImage());
    }

    private void refreshEqRect(Character character, Rectangle rectangle, Image image) {
        rectangle.setWidth(image.getWidth());
        rectangle.setHeight(image.getHeight());
        rectangle.setFill(new ImagePattern(image));
    }

    private void refreshInventory(Character character) {
        Pane pane = (Pane) panel.getCatchedItemRect().getParent();
        redrawInventoryRect(pane);

        int[] itemInvPos;
        Rectangle itemInvFirstRect;
        for (Item item: character.getInventory().keySet()) {
            itemInvPos = character.getInventory().get(item);
            itemInvFirstRect = IsoMapClickController.calcInventoryScreenRect(panel, itemInvPos);
            Rectangle inventoryItemRect = new Rectangle(itemInvFirstRect.getX(), itemInvFirstRect.getY(),
                    item.getImage().getWidth(), item.getImage().getHeight());
            inventoryItemRect.setFill(new ImagePattern(item.getImage()));
            pane.getChildren().add(inventoryItemRect);
        }
        redrawInventoryClickRect(pane);
    }

    private void redrawInventoryRect(Pane pane) {
        if (!inventoryRectsSet) {
            Rectangle invFirstRect = IsoMapClickController.calcInventoryScreenRect(panel, new int[]{0, 0});
            inventoryRect.setX(invFirstRect.getX());
            inventoryRect.setY(invFirstRect.getY());
            initInventoryClickRectangle();
        }
        pane.getChildren().remove(inventoryRect);
        pane.getChildren().add(inventoryRect);
    }

    private void redrawInventoryClickRect(Pane pane) {
        if (!inventoryRectsSet) {
            initInventoryClickRectangle();
            inventoryRectsSet = true;
        }
        pane.getChildren().remove(inventoryClickRect);
        pane.getChildren().add(inventoryClickRect);
    }

    private void initInventoryClickRectangle() {
        inventoryClickRect = panel.getInventoryRectangle();
        inventoryClickRect.setOpacity(0);
        IsoMapClickController.initInventoryClick(inventoryClickRect);
        inventoryClickRect.setVisible(true);
    }


    private List<Character> calcChosenCharacters(){
        List<Character> chosenCharacters = new ArrayList<>();
        for (Character character: characters) {
            if (character.isChosen()) {
                chosenCharacters.add(character);
            }
        }
        return chosenCharacters;
    }
}
