package UserInterface.components;

import Controllers.AndGateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * This class is a Wrapper for the AndGate component which is created using the .fxml file
 * It loads the fxml and then adds the component to the current Pane.
 * It also accesses the reference to the controller of the component to access the different components within it.
 */
public class AndGate extends AnchorPane {

    // Reference to the controller of the component.
    private AndGateController andGateController;

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

    public ImageView getOutputConnector() {
        return andGateController.getOutputConnector();
    }

    public ImageView getInputA() {
        return andGateController.getInputA();
    }

    public ImageView getInputB() {
        return andGateController.getInputB();
    }

    @Override
    public String toString() {
        return andGateController.toString();
    }
}
