package UserInterface;

import Constants.SizeConstants;
import Controllers.DragEventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class DashboardController implements SizeConstants {

    private DragEventHandler dragEventHandler;
    @FXML
    private AnchorPane btnAndGate,btnOrGate,btnNotGate,btnNandGate,btnNorGate,btnXorGate,btnXnorGate;
    @FXML
    private Pane pnlCanvas;

    @FXML
    public void initialize() {
        this.dragEventHandler = new DragEventHandler(this);
    }

    public AnchorPane getAndGateButton() {
        return this.btnAndGate;
    }
    public AnchorPane getOrGateButton() {
        return this.btnAndGate;
    }
    public AnchorPane getNotGateButton() {
        return this.btnNotGate;
    }
    public AnchorPane getNandGateButton() {
        return this.btnNandGate;
    }
    public AnchorPane getNorButtonGate() {
        return this.btnNorGate;
    }
    public AnchorPane getXorGateButton() {
        return this.btnXorGate;
    }
    public AnchorPane getXnorGateButton() {
        return this.btnXnorGate;
    }
    public Pane getCanvasPanel() {
        return this.pnlCanvas;
    }
}
