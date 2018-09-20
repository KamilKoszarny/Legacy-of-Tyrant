package controller.isoView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.map.Map;
import viewIso.IsoViewer;

import java.awt.*;
import java.io.IOException;

public class IsoViewController {

    @FXML
    private Canvas canvas;

    private Map map;
    private Dimension screenSize;
    private IsoViewer isoViewer;
    private static final int PANEL_HEIGHT = 200;

    public IsoViewController(Stage primaryStage, Map map) throws IOException {
        this.map = map;
        openWindow(primaryStage);
        isoViewer = new IsoViewer(map, canvas);
        IsoMapMoveController isoMapMoveController = new IsoMapMoveController(isoViewer, canvas);
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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        canvas.setWidth(screenSize.width);
        canvas.setHeight(screenSize.height - PANEL_HEIGHT);

        primaryStage.show();
    }

    @FXML
    void initialize(){

    }


}
