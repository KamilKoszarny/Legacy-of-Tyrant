package controller.isoView.isoPanel;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Panel {

    private List<Label> charLabels;
    private List<ProgressBar> charBars;
    private Rectangle portraitRect;

    public Panel(List<Label> charLabels, List<ProgressBar> charBars, Rectangle portraitRect) {
        this.charLabels = charLabels;
        this.charBars = charBars;
        this.portraitRect = portraitRect;
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
}
