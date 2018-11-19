package viewIso.panel;

import controller.isoView.isoPanel.Panel;
import javafx.scene.control.ProgressBar;
import model.Battle;
import model.character.Character;
import model.items.Item;

import java.awt.*;
import java.util.List;

public class PanelViewer {

    private Panel panel;
    private CharDescriptor charDescriptor;
    private Battle battle;
    Item catchItem;
    Point catchPoint;

    public PanelViewer(Panel panel, List<Character> characters, Battle battle) {
        this.panel = panel;
        this.battle = battle;
        charDescriptor = new CharDescriptor(this.panel, characters);
    }

    public void refresh() {
        charDescriptor.refresh();
        if (catchItem != null)
            drawCatchedItem();
    }

    private void drawCatchedItem() {
        

    }

    public void setCatchItem(Item catchItem) {
        this.catchItem = catchItem;
    }

    public void setCatchPoint(Point catchPoint) {
        this.catchPoint = catchPoint;
    }
}
