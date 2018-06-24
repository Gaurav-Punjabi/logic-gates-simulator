package UserInterface.components;

import BackEnd.PowerSource;
import Controllers.PowerSourceController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class PowerSourceComponent extends PowerSource {

    private PowerSourceController controller;

    public PowerSourceComponent() {
        super(true);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(this.getClass().getResource("PowerSourceComponent.fxml"));
            Parent root = fxmlLoader.load();
            this.controller = fxmlLoader.<PowerSourceController>getController();
            this.getChildren().add(root);
            initComponents();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void initComponents() {
        this.controller.getPowerSourceImage().setOnMouseClicked(this::powerSourceMouseClicked);
    }

    private void powerSourceMouseClicked(MouseEvent mouseEvent) {
        this.togglePower();
        if(this.getOutputValue()) {
            this.controller.getPowerSourceImage().setImage(new Image("resources/icons/positive-battery.png"));
        } else {
            this.controller.getPowerSourceImage().setImage(new Image("resources/icons/negative-battery.png"));
        }
    }

    public ImageView getPowerSourceImage() {
        return this.controller.getPowerSourceImage();
    }

    public ImageView getOutputConnector() {
        return this.controller.getOutputConnector();
    }
}
