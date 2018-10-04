package controller.isoView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.IsoBattleLoop;
import model.character.Character;
import model.map.Map;
import viewIso.IsoViewer;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IsoViewController {

    @FXML
    private Canvas mapCanvas, topBorderCanvas, rightBorderCanvas, bottomBorderCanvas, leftBorderCanvas,
            topRightBorderCanvas, bottomRightBorderCanvas, bottomLeftBorderCanvas, topLeftBorderCanvas;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private HBox panel;
    @FXML
    private VBox charBasicsVBox;
    @FXML
    private Label charName, charType, chatClass;
    private List<Label> charLabels = Arrays.asList(charName, charType, chatClass);

    private static final int PANEL_HEIGHT = 200;

    private IsoViewer isoViewer;
    private IsoBattleLoop isoBattleLoop;

    public IsoViewController(Stage primaryStage, IsoBattleLoop isoBattleLoop, Map map, List<Character> characters) throws IOException {
        this.isoBattleLoop = isoBattleLoop;
        openWindow(primaryStage);
        List<Canvas> borderCanvases = new ArrayList<>(Arrays.asList(topBorderCanvas, rightBorderCanvas, bottomBorderCanvas, leftBorderCanvas,
                topRightBorderCanvas, bottomRightBorderCanvas, bottomLeftBorderCanvas, topLeftBorderCanvas));
        isoViewer = new IsoViewer(map, mapCanvas, characters);
        new IsoMapMoveController(mapCanvas, borderCanvases, panel, isoBattleLoop).initialize();
        new IsoMapClickController(mapCanvas, charLabels, isoBattleLoop, characters).initialize();
    }

    private void openWindow(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/fxml/IsoView.fxml"));

        fxmlLoader.setController(this);

        Pane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("BattleIsoView");
        primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        mainPane.setPrefWidth(screenSize.width);
        mainPane.setPrefWidth(screenSize.height);
        mapCanvas.setWidth(screenSize.width);
        mapCanvas.setHeight(screenSize.height - PANEL_HEIGHT);

        panel.setLayoutX(0);
        panel.setLayoutY(screenSize.height - PANEL_HEIGHT);
        panel.setPrefWidth(screenSize.width);
        panel.setPrefHeight(PANEL_HEIGHT);
        panel.setBorder(new Border(new BorderStroke(Color.BROWN,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        primaryStage.show();
    }

    @FXML
    void initialize(){

    }

    public IsoViewer getIsoViewer() {
        return isoViewer;
    }

    public HBox getPanel() {
        return panel;
    }
}
