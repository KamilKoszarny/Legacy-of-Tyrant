package controller.isoView.isoPanel;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.awt.*;

public class PanelController {

    public static final int PANEL_HEIGHT = 200;
    private static final Color PANEL_COLOR = Color.DARKGRAY;

    private HBox panel;


    public PanelController(HBox panelHbox) {
        panel = panelHbox;

    }

    public void initialize(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        panel.setLayoutX(0);
        panel.setLayoutY(screenSize.height - PANEL_HEIGHT);
        panel.setPrefWidth(screenSize.width);
        panel.setPrefHeight(PANEL_HEIGHT);
        panel.setBackground(new Background(new BackgroundFill(PANEL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        panel.setBorder(new Border(new BorderStroke(PANEL_COLOR.darker(),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
    }
}
