package controller.isoView;

import controller.isoView.isoMap.IsoMapClickController;
import controller.isoView.isoMap.IsoMapHoverController;
import controller.isoView.isoMap.IsoMapKeyController;
import controller.isoView.isoMap.IsoMapMoveController;
import controller.isoView.isoPanel.Panel;
import controller.isoView.isoPanel.PanelController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.IsoBattleLoop;
import model.character.Character;
import model.map.Map;
import viewIso.IsoViewer;
import viewIso.PanelViewer;

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
    private HBox panelHBox;
    @FXML
    private VBox charBasicsVBox;
    @FXML
    private javafx.scene.control.Label name, type, charClass;

    private static final int PANEL_HEIGHT = 200;

    private IsoViewer isoViewer;
    private PanelViewer panelViewer;
    private IsoBattleLoop isoBattleLoop;

    public IsoViewController(Stage primaryStage, IsoBattleLoop isoBattleLoop, Map map, List<Character> characters) throws IOException {
        this.isoBattleLoop = isoBattleLoop;
        openWindow(primaryStage);

        List<Canvas> borderCanvases = new ArrayList<>(Arrays.asList(topBorderCanvas, rightBorderCanvas, bottomBorderCanvas, leftBorderCanvas,
                topRightBorderCanvas, bottomRightBorderCanvas, bottomLeftBorderCanvas, topLeftBorderCanvas));
        List<Label> charLabels = Arrays.asList(name, type, charClass);
        controller.isoView.isoPanel.Panel panel = new Panel(charLabels);

        isoViewer = new IsoViewer(map, mapCanvas, characters);
        panelViewer = new PanelViewer(panel, characters);

        new IsoMapMoveController(mapCanvas, borderCanvases, panelHBox, isoBattleLoop).initialize();
        new IsoMapClickController(mapCanvas, isoBattleLoop, characters).initialize();
        new IsoMapHoverController(mapCanvas, isoBattleLoop, characters).initialize();
        new IsoMapKeyController(mapCanvas.getScene(), isoViewer, isoBattleLoop).initiazile();
        new PanelController(panel).initialize();
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

        panelHBox.setLayoutX(0);
        panelHBox.setLayoutY(screenSize.height - PANEL_HEIGHT);
        panelHBox.setPrefWidth(screenSize.width);
        panelHBox.setPrefHeight(PANEL_HEIGHT);
        panelHBox.setBorder(new Border(new BorderStroke(Color.BROWN,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        primaryStage.show();
    }

    @FXML
    void initialize(){
    }

    public IsoViewer getIsoViewer() {
        return isoViewer;
    }

    public PanelViewer getPanelViewer() {
        return panelViewer;
    }
}
