package Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


public class OrGateController {
    @FXML
    private ImageView orGate,inputA,inputB,outputConnector;

    public ImageView getOrGate() {
        return orGate;
    }

    public ImageView getInputB() {
        return inputB;
    }

    public ImageView getInputA() {
        return inputA;
    }

    public ImageView getOutputConnector() {
        return outputConnector;
    }
}
