package UserInterface.components;

import Controllers.OrGateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class OrGate extends AnchorPane {

    private OrGateController orGateController;

    public OrGate() {
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
    public ImageView getInputA() {
        return this.orGateController.getInputA();
    }
    public ImageView getInputB() {
        return this.orGateController.getInputB();
    }
    public ImageView getOutputConnector() {
        return this.orGateController.getOutputConnector();
    }
}
