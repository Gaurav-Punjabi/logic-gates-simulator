package Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

/**
 * This is a controller for the AndGate component it basically creates a link between AndGate Component and the fxml UI.
 */
public class AndGateController {
    @FXML
    private ImageView andGate,outputConnector,inputA,inputB;
    @FXML
    public void initialize() {
        System.out.println("andGate = " + andGate);
    }

    /*******************************************************************************************************************
     *                                              GETTERS & SETTERS
     *******************************************************************************************************************
     */
    public ImageView getAndGate() {
        return andGate;
    }

    public ImageView getOutputConnector() {
        return outputConnector;
    }

    public ImageView getInputA() {
        return inputA;
    }

    public ImageView getInputB() {
        return inputB;
    }

    /**
     * Just overriding the toString method for making the testing easier.
     * @return : the string representation of this object.
     */
    @Override
    public String toString() {
        return "And Gate : " + this.andGate + " " + this.outputConnector + " " + this.inputA + " " + this.inputB;
    }
}
