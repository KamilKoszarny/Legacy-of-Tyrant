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
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.IsoBattleLoop;
import model.character.Character;
import model.map.Map;
import viewIso.IsoViewer;
import viewIso.panel.PanelViewer;

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
    topRightBorderCanvas, bottomRightBorderCanvas, bottomLeftBorderCanvas, topLeftBorderCanvas;

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
    private Label vimLabel, strengthLabel, durabilityLabel, staminaLabel, dexterityLabel, eyeLabel, armLabel, agilityLabel, intelligenceLabel, knowledgeLabel, focusLabel, spiritLabel;
    @FXML
    private Rectangle helmetRect, weaponRect, armorRect, shieldRect, glovesRect, bootsRect, amuletRect, ring1Rect, beltRect, ring2Rect, spareWeaponRect, spareShieldRect;
    @FXML
    private Rectangle caughtItemRect;
    @FXML
    private Pane inventoryGridPane, defenceGridPane;
    @FXML
    private Label loadLabel, speedLabel, attackSpeedLabel, rangeLabel, magicResistanceLabel, dmgMinLabel, dmgMaxLabel, accuracyLabel, avoidanceLabel;
    @FXML
    private Rectangle minimapRect;

    private IsoViewer isoViewer;
    private PanelViewer panelViewer;
    private IsoBattleLoop isoBattleLoop;

    public IsoViewController(Stage primaryStage, IsoBattleLoop isoBattleLoop, Map map, List<Character> characters) throws IOException {
        this.isoBattleLoop = isoBattleLoop;
        openWindow(primaryStage);

        Panel panel = preparePanel();
        isoViewer = new IsoViewer(map, mapCanvas, characters);
        panelViewer = new PanelViewer(panel, characters, caughtItemRect);

        new PanelController(panelHBox).initialize();
        new IsoMapMoveController(mapCanvas, groupBorderCanvases(), panelHBox, isoBattleLoop).initialize();
        new IsoMapClickController(mapCanvas, panel, isoBattleLoop, characters).initialize();
        new IsoMapHoverController(mapCanvas, isoBattleLoop, characters).initialize();
        new IsoMapKeyController(mapCanvas.getScene(), isoViewer, isoBattleLoop).initiazile();
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
        List<Label> charLabels = Arrays.asList(nameLabel, typeLabel, charClassLabel,
                hitPointsLabel, manaLabel, vigorLabel,
                vimLabel, strengthLabel, durabilityLabel, staminaLabel, dexterityLabel, eyeLabel, armLabel, agilityLabel, intelligenceLabel, knowledgeLabel, focusLabel, spiritLabel,
                loadLabel, speedLabel, attackSpeedLabel, rangeLabel, magicResistanceLabel, dmgMinLabel, dmgMaxLabel, accuracyLabel, avoidanceLabel
    );
        List<ProgressBar> charBars = Arrays.asList(hitPointsProgressBar, manaProgressBar, vigorProgressBar);

        List<Rectangle> itemRectangles = Arrays.asList(weaponRect, spareWeaponRect, shieldRect, armorRect, helmetRect,
                glovesRect, bootsRect, beltRect, amuletRect, ring1Rect, ring2Rect, spareShieldRect);


        return new Panel(charLabels, charBars, charPictRect, charPictBackgroundRect,
                helmetRect, weaponRect, armorRect, shieldRect, glovesRect, bootsRect,
                amuletRect, ring1Rect, beltRect, ring2Rect, spareWeaponRect, spareShieldRect, caughtItemRect, inventoryGridPane, minimapRect);
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
