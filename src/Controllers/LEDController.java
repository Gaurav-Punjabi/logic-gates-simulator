package Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class LEDController {
    @FXML
    private ImageView ledImage,inputConnector;

    public ImageView getLedImage() {
        return ledImage;
    }

    public ImageView getInputConnector() {
        return inputConnector;
    }
}
