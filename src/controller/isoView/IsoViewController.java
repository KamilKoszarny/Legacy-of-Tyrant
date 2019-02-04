package controller.isoView;

import controller.isoView.isoMap.IsoMapClickController;
import controller.isoView.isoMap.IsoMapHoverController;
import controller.isoView.isoMap.IsoMapKeyController;
import controller.isoView.isoMap.IsoMapBorderHoverController;
import controller.isoView.isoPanel.Panel;
import controller.isoView.isoPanel.PanelController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IsoViewController {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Canvas mapCanvas, topBorderCanvas, rightBorderCanvas, bottomBorderCanvas, leftBorderCanvas,
            topRightBorderCanvas, bottomRightBorderCanvas, bottomLeftBorderCanvas, topLeftBorderCanvas,
            minimapFogCanvas;

    @FXML
    private HBox panelHBox;
    @FXML
    private Rectangle charPictRect, charPictBackgroundRect;
    @FXML
    private javafx.scene.control.Label nameLabel, typeLabel, charClassLabel;
    @FXML
    private ProgressBar hitPointsProgressBar, manaProgressBar, vigorProgressBar;
    @FXML
    private Label hitPointsLabel, manaLabel, vigorLabel;
    @FXML
    private Label vimLabel, strengthLabel, durabilityLabel, staminaLabel,
            dexterityLabel, eyeLabel, armLabel, agilityLabel,
            intelligenceLabel, knowledgeLabel, focusLabel, spiritLabel;
    @FXML
    private Rectangle helmetRect, weaponRect, armorRect, shieldRect, glovesRect, bootsRect,
            amuletRect, ring1Rect, beltRect, ring2Rect, spareWeaponRect, spareShieldRect;
    @FXML
    private Rectangle heldItemRect;
    @FXML
    private Pane inventoryGridPane;
    @FXML
    private Label loadLabel, dmgMinLabel, dmgMaxLabel, accuracyLabel, attackSpeedLabel, rangeLabel, speedMaxLabel,
            avoidanceLabel, blockLabel, bodyArmorLabel, headArmorLabel, armsArmorLabel, legsArmorLabel,
            fireResistanceLabel, waterResistanceLabel, windResistanceLabel, earthResistanceLabel, magicResistanceLabel;
    @FXML
    private Rectangle minimapRect, minimapPosRect;

    private Panel panel;

    public IsoViewController(Stage primaryStage) throws IOException {
        openWindow(primaryStage);
        panel = preparePanel();

        new PanelController(panelHBox, panel);
        new IsoMapBorderHoverController(mapCanvas, groupBorderCanvases(), panelHBox);
        new IsoMapClickController(mapCanvas);
        new IsoMapHoverController(mapCanvas);
        new IsoMapKeyController(mapCanvas.getScene());
    }

    private void openWindow(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/fxml/IsoView.fxml"));
        fxmlLoader.setController(this);

        Pane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("fxml/mainStyle.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("BattleIsoView");
        primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        mainPane.setPrefWidth(screenSize.width);
        mainPane.setPrefWidth(screenSize.height);
        mapCanvas.setWidth(screenSize.width);
        mapCanvas.setHeight(screenSize.height - PanelController.PANEL_HEIGHT);

        primaryStage.show();
    }

    private List<Canvas> groupBorderCanvases() {
        return new ArrayList<>(Arrays.asList(topBorderCanvas, rightBorderCanvas, bottomBorderCanvas, leftBorderCanvas,
                topRightBorderCanvas, bottomRightBorderCanvas, bottomLeftBorderCanvas, topLeftBorderCanvas));
    }

    private Panel preparePanel() {
        List<Label> charStatsLabels = Arrays.asList(
                nameLabel, typeLabel, charClassLabel,
                hitPointsLabel, manaLabel, vigorLabel,
                vimLabel, strengthLabel, durabilityLabel, staminaLabel,
                dexterityLabel, eyeLabel, armLabel, agilityLabel,
                intelligenceLabel, knowledgeLabel, focusLabel, spiritLabel,
                loadLabel, dmgMinLabel, dmgMaxLabel, accuracyLabel, attackSpeedLabel, rangeLabel, speedMaxLabel,
                avoidanceLabel, blockLabel, bodyArmorLabel, headArmorLabel, armsArmorLabel, legsArmorLabel,
                fireResistanceLabel, waterResistanceLabel, windResistanceLabel, earthResistanceLabel, magicResistanceLabel);
        List<ProgressBar> charBars = Arrays.asList(hitPointsProgressBar, manaProgressBar, vigorProgressBar);
        List<Rectangle> itemRectangles = Arrays.asList(weaponRect, spareWeaponRect, shieldRect, armorRect, helmetRect, glovesRect, bootsRect,
                beltRect, amuletRect, ring1Rect, ring2Rect, spareShieldRect);



        return new Panel(charStatsLabels, charBars, charPictRect, charPictBackgroundRect,
                itemRectangles, heldItemRect, inventoryGridPane,
                minimapRect, minimapPosRect, minimapFogCanvas);
    }

    public Canvas getMapCanvas() {
        return mapCanvas;
    }

    public Panel getPanel() {
        return panel;
    }
}