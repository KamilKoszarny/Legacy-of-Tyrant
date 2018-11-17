package controller.isoView.isoPanel;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.util.List;

public class Panel {

    private List<Label> charLabels;
    private List<ProgressBar> charBars;

    public Panel(List<Label> charLabels, List<ProgressBar> charBars) {
        this.charLabels = charLabels;
        this.charBars = charBars;
    }

    public List<Label> getCharLabels() {
        return charLabels;
    }

    public List<ProgressBar> getCharBars() {
        return charBars;
    }
}
