package controller.isoView.isoPanel;

import javafx.scene.control.Label;

import java.util.List;

public class Panel {

    private List<Label> charLabels;

    public Panel(List<Label> charLabels) {
        this.charLabels = charLabels;
    }

    public List<Label> getCharLabels() {
        return charLabels;
    }
}
