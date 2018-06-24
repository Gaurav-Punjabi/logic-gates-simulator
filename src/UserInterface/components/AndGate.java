package UserInterface.components;

import BackEnd.BinaryGate;
import BackEnd.CircuitComponent;
import Controllers.AndGateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.io.IOException;

/**
 * This class is a Wrapper for the AndGate component which is created using the .fxml file
 * It loads the fxml and then adds the component to the current Pane.
 * It also accesses the reference to the controller of the component to access the different components within it.
 */
public class AndGate extends BinaryGate {

    // Reference to the controller of the component.
    private AndGateController andGateController;

    /*******************************************************************************************************************
     *                                                   CONSTRUCOR
     ******************************************************************************************************************/
    public AndGate() {
        try {
            // Loading the component.
            FXMLLoader loader = new FXMLLoader();
            // Specifying the location.
            loader.setLocation(this.getClass().getResource("AndGate.fxml"));
            Parent root = loader.load();
            // Accessing the controller.
            this.andGateController = loader.<AndGateController>getController();
            AnchorPane.setTopAnchor(root,0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            this.getChildren().add(root);
        } catch (IOException ioe) {
            System.out.println("ioe.getMessage() = " + ioe.getMessage());
        }
    }

    public ImageView getAndGate() {
        return andGateController.getAndGate();
    }

    public ImageView getOutputImage() {
        return andGateController.getOutputConnector();
    }

    public ImageView getInputAImage() {
        return andGateController.getInputA();
    }
    public ImageView getInputBImage() {
        return andGateController.getInputB();
    }

    @Override
    public String toString() {
        return andGateController.toString();
    }
}
