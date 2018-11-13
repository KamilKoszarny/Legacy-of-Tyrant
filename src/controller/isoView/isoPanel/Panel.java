package controller.isoView.isoPanel;

import javafx.scene.control.Label;

import java.util.List;

public class Panel {

    private List<Label> charBasicLabels;

    public Panel(List<Label> charBasicLabels) {
        this.charBasicLabels = charBasicLabels;
    }

    public List<Label> getCharBasicLabels() {
        return charBasicLabels;
    }
}
