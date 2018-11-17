package viewIso.panel;

import controller.isoView.isoPanel.Panel;
import javafx.scene.control.ProgressBar;
import model.character.Character;

import java.util.List;

public class PanelViewer {

    private Panel panel;
    private CharDescriptor charDescriptor;

    public PanelViewer(Panel panel, List<Character> characters) {
        this.panel = panel;
        charDescriptor = new CharDescriptor(this.panel, characters);
    }

    public void refresh() {
        charDescriptor.refresh();
    }
}
