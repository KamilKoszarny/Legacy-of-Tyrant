package controller.isoview.panel;


public class PanelController {

    private static Panel panel;

    public PanelController(Panel panel) {
        PanelController.panel = panel;

        new MinimapController(panel);
        new ItemClickController(panel);
        new NextTurnButtonController(panel);
    }

    public static Panel getPanel() {
        return panel;
    }

}
