package UserInterface.components;

import BackEnd.BinaryGate;
import Controllers.OrGateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OrGateComponent extends BinaryGate {

    private OrGateController orGateController;

    public OrGateComponent() {
        try {
            //Initializing the FXML
            FXMLLoader fxmlLoader = new FXMLLoader();
            //Specifying the location.
            fxmlLoader.setLocation(this.getClass().getResource("OrGate.fxml"));
            // Loading the fxml.
            Parent root = fxmlLoader.load();
            //Accessing the Controller of the component
            this.orGateController = fxmlLoader.<OrGateController>getController();
            //Setting the component on the currentPane.
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            this.getChildren().add(root);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public ImageView getOrGate() {
        return this.orGateController.getOrGate();
    }
    public ImageView getInputAImage() {
        return this.orGateController.getInputA();
    }
    public ImageView getInputBImage() {
        return this.orGateController.getInputB();
    }
    public ImageView getOutputConnector() {
        return this.orGateController.getOutputConnector();
    }
}
