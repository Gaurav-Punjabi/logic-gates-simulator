package UserInterface.components;

import BackEnd.LED;
import Controllers.LEDController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * This is the component wrapper class that is used to load the fxml UI file and add it to the anchorPane.
 */
public class LEDComponent extends LED {
    private LEDController controller;
    public LEDComponent() {
        super(false);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(this.getClass().getResource("LEDComponent.fxml"));
            Parent root = fxmlLoader.load();
            this.controller = fxmlLoader.<LEDController>getController();
            this.getChildren().add(root);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void setLed(final boolean input) {
        super.setInputA(input);
        if(input) {
            this.controller.getLedImage().setImage(new Image("/resources/icons/green-led.png"));
        } else {
            this.controller.getLedImage().setImage(new Image("/resources/icons/red-led.png"));
        }
    }

    public ImageView getLEDImage() {
        return this.controller.getLedImage();
    }
    public ImageView getInputImage() {
        return this.controller.getInputConnector();
    }

}
