package Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


public class PowerSourceController {
    @FXML
    private ImageView powerSource, outputConnector;

    @FXML
    public void initialize() {
        System.out.println("powerSource = " + outputConnector);
    }

    public ImageView getPowerSourceImage() {
        return this.powerSource;
    }

    public ImageView getOutputConnector() {
        return this.outputConnector;
    }
}
