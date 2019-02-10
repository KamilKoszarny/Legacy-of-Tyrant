package controller.isoView.isoPanel;

import javafx.scene.input.MouseEvent;
import viewIso.map.MapDrawer;
import viewIso.panel.MinimapViewer;

class MinimapController {

    private static Panel panel;

    MinimapController(Panel panel) {
        MinimapController.panel = panel;
        initialize();
    }

    private static void initialize(){
        initMinimapClick();
    }

    private static void initMinimapClick() {
        panel.getMiniMapRect().setOnMousePressed(MinimapController::minimapMouseAction);
        panel.getMiniMapRect().setOnMouseDragged(MinimapController::minimapMouseAction);
    }

    private static void minimapMouseAction(MouseEvent mouseEvent) {
        int clickScreenX = (int) (MinimapViewer.minimapScreenSize / 2 + (mouseEvent.getX() - mouseEvent.getY()) / Math.sqrt(2));
        int clickScreenY = (int) ((mouseEvent.getX() + mouseEvent.getY()) / Math.sqrt(2));

        MapDrawer.setZeroScreenPosition(
                (-clickScreenX + MinimapViewer.getRectangle().getWidth() / 2 + MinimapViewer.getPositionRectangle().getWidth())
                        / MinimapViewer.minimapToScreenRatioX,
                (-clickScreenY + MinimapViewer.getPositionRectangle().getHeight() / 2 + MapDrawer.getMap().getHeightType().getHilly() / 10.)
                        / MinimapViewer.minimapToScreenRatioY);
    }
}