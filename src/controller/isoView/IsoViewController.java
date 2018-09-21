package controller.isoView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.IsoBattleLoop;
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

    private IsoViewer isoViewer;
    private IsoBattleLoop isoBattleLoop;
    private static final int PANEL_HEIGHT = 200;

    public IsoViewController(Stage primaryStage, Map map, IsoBattleLoop isoBattleLoop) throws IOException {
        this.isoBattleLoop = isoBattleLoop;
        openWindow(primaryStage);
        List<Canvas> borderCanvases = new ArrayList<>(Arrays.asList(topBorderCanvas, rightBorderCanvas, bottomBorderCanvas, leftBorderCanvas,
                topRightBorderCanvas, bottomRightBorderCanvas, bottomLeftBorderCanvas, topLeftBorderCanvas));
        isoViewer = new IsoViewer(map, mapCanvas);
        IsoMapMoveController isoMapMoveController = new IsoMapMoveController(mapCanvas, borderCanvases, isoBattleLoop);
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
        mapCanvas.setWidth(screenSize.width);
        mapCanvas.setHeight(screenSize.height - PANEL_HEIGHT);

        primaryStage.show();
    }

    @FXML
    void initialize(){

    }

    public IsoViewer getIsoViewer() {
        return isoViewer;
    }
}
