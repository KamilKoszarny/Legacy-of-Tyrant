package controller.isoView.isoPanel;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.List;

public class Panel {

    private List<Label> charLabels;
    private List<ProgressBar> charBars;
    private Rectangle portraitRect, charPictBackgroundRect;
    private Rectangle helmetRect, weaponRect, armorRect, shieldRect, glovesRect, bootsRect, amuletRect, ring1Rect, beltRect, ring2Rect, spareWeaponRect, spareShieldRect;
    private Rectangle catchedItemRect;

    public Panel(List<Label> charLabels, List<ProgressBar> charBars, Rectangle portraitRect, Rectangle charPictBackgroundRect,
                 Rectangle helmetRect, Rectangle weaponRect, Rectangle armorRect, Rectangle shieldRect,
                 Rectangle glovesRect, Rectangle bootsRect, Rectangle amuletRect, Rectangle ring1Rect,
                 Rectangle beltRect, Rectangle ring2Rect, Rectangle spareWeaponRect, Rectangle spareShieldRect,
                 Rectangle caughtItemRect) {
        this.charLabels = charLabels;
        this.charBars = charBars;
        this.portraitRect = portraitRect;
        this.charPictBackgroundRect = charPictBackgroundRect;
        this.helmetRect = helmetRect;
        this.weaponRect = weaponRect;
        this.armorRect = armorRect;
        this.shieldRect = shieldRect;
        this.glovesRect = glovesRect;
        this.bootsRect = bootsRect;
        this.amuletRect = amuletRect;
        this.ring1Rect = ring1Rect;
        this.beltRect = beltRect;
        this.ring2Rect = ring2Rect;
        this.spareWeaponRect = spareWeaponRect;
        this.spareShieldRect = spareShieldRect;
        this.catchedItemRect = caughtItemRect;
        caughtItemRect.setVisible(false);
    }

    public List<Label> getCharLabels() {
        return charLabels;
    }

    public List<ProgressBar> getCharBars() {
        return charBars;
    }

    public Rectangle getPortraitRect() {
        return portraitRect;
    }

    public Rectangle getCharPortraitBackgroundRect() {
        return charPictBackgroundRect;
    }

    public Rectangle getHelmetRect() {
        return helmetRect;
    }

    public Rectangle getWeaponRect() {
        return weaponRect;
    }

    public Rectangle getArmorRect() {
        return armorRect;
    }

    public Rectangle getShieldRect() {
        return shieldRect;
    }

    public Rectangle getGlovesRect() {
        return glovesRect;
    }

    public Rectangle getBootsRect() {
        return bootsRect;
    }

    public Rectangle getAmuletRect() {
        return amuletRect;
    }

    public Rectangle getRing1Rect() {
        return ring1Rect;
    }

    public Rectangle getBeltRect() {
        return beltRect;
    }

    public Rectangle getRing2Rect() {
        return ring2Rect;
    }

    public Rectangle getSpareWeaponRect() {
        return spareWeaponRect;
    }

    public Rectangle getSpareShieldRect() {
        return spareShieldRect;
    }

    public List<Rectangle> getItemRectangles() {
        return Arrays.asList(weaponRect, spareWeaponRect, shieldRect, armorRect, helmetRect,
                glovesRect, bootsRect, beltRect, amuletRect, ring1Rect, ring2Rect, spareShieldRect);
    }

    public Rectangle getCatchedItemRect() {
        return catchedItemRect;
    }
}