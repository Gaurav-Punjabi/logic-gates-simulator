package UserInterface.components;

import BackEnd.BinaryGate;
import Controllers.AndGateController;
import Controllers.NorGateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NorGateComponent extends BinaryGate {
    private NorGateController norGateController;
    /*******************************************************************************************************************
     *                                                   CONSTRUCOR
     ******************************************************************************************************************/
    public NorGateComponent() {
        try {
            // Loading the component.
            FXMLLoader loader = new FXMLLoader();
            // Specifying the location.
            loader.setLocation(this.getClass().getResource("NorGate.fxml"));
            Parent root = loader.load();
            // Accessing the controller.
            this.norGateController = loader.<NorGateController>getController();
            AnchorPane.setTopAnchor(root,0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            this.getChildren().add(root);
        } catch (IOException ioe) {
            System.out.println("ioe.getMessage() = " + ioe.getMessage());
        }
    }

    public ImageView getNorGate() {
        return norGateController.getNorGate();
    }

    public ImageView getOutputImage() {
        return norGateController.getOutputConnector();
    }

    public ImageView getInputAImage() {
        return norGateController.getInputA();
    }
    public ImageView getInputBImage() {
        return norGateController.getInputB();
    }
}
