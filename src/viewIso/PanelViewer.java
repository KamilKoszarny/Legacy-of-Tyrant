package viewIso;

import controller.isoView.Panel;
import model.character.Character;
import viewIso.characters.CharDescriptor;

import java.util.List;

public class PanelViewer {

    private Panel panel;
    private CharDescriptor charDescriptor;

    public PanelViewer(Panel panel, List<Character> characters) {
        this.panel = panel;
        charDescriptor = new CharDescriptor(panel.getCharBasicLabels(), characters);
    }

    public void refresh() {
        charDescriptor.refresh();
    }
}
