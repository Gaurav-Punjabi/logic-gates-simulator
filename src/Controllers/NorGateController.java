package Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


public class NorGateController {
    @FXML
    private ImageView inputA,inputB,norGate,outputConnector;

    public ImageView getInputB() {
        return inputB;
    }

    public ImageView getInputA() {
        return inputA;
    }

    public ImageView getOutputConnector() {
        return outputConnector;
    }

    public ImageView getNorGate() {
        return norGate;
    }
}
