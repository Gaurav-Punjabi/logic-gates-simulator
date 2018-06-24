package UserInterface.components;

import BackEnd.BinaryGate;
import Controllers.AndGateController;
import Controllers.NandGateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NandGateComponent extends BinaryGate {
    private NandGateController nandGateController;
    public NandGateComponent() {
        try {
            // Loading the component.
            FXMLLoader loader = new FXMLLoader();
            // Specifying the location.
            loader.setLocation(this.getClass().getResource("NandGate.fxml"));
            Parent root = loader.load();
            // Accessing the controller.
            this.nandGateController = loader.<NandGateController>getController();
            AnchorPane.setTopAnchor(root,0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            this.getChildren().add(root);
        } catch (IOException ioe) {
            System.out.println("ioe.getMessage() = " + ioe.getMessage());
        }
    }

    public ImageView getNandGate() {
        return nandGateController.getNandGate();
    }

    public ImageView getOutputImage() {
        return nandGateController.getOutputConnector();
    }

    public ImageView getInputAImage() {
        return nandGateController.getInputA();
    }
    public ImageView getInputBImage() {
        return nandGateController.getInputB();
    }
}
