package UserInterface;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Test extends Application {

    private Pane root = new Pane();
    private DoubleProperty mouseX = new SimpleDoubleProperty();
    private DoubleProperty mouseY = new SimpleDoubleProperty();

    private List<Circle> nodes;
    private Line currentLine = null;
    private boolean dragActive = false;

    private Parent createContent() {
        root.setPrefSize(600, 600);

        nodes = Arrays.asList(
                new Circle(50, 50, 50, Color.BLUE),
                new Circle(500, 100, 50, Color.RED),
                new Circle(400, 500, 50, Color.GREEN)
        );

        nodes.forEach(root.getChildren()::add);

        return root;
    }

    private Optional<Circle> findNode(double x, double y) {
        return nodes.stream().filter(n -> n.contains(x, y)).findAny();
    }

    private void startDrag(Circle node) {
        if (dragActive)
            return;

        dragActive = true;
        currentLine = new Line();
        currentLine.setUserData(node);
        currentLine.setStartX(node.getCenterX());
        currentLine.setStartY(node.getCenterY());
        currentLine.endXProperty().bind(mouseX);
        currentLine.endYProperty().bind(mouseY);

        root.getChildren().add(currentLine);
    }

    private void stopDrag(Circle node) {
        dragActive = false;

        if (currentLine.getUserData() != node) {
            // distinct node
            currentLine.endXProperty().unbind();
            currentLine.endYProperty().unbind();
            currentLine.setEndX(node.getCenterX());
            currentLine.setEndY(node.getCenterY());
            currentLine = null;
        } else {
            // same node
            stopDrag();
        }
    }

    private void stopDrag() {
        dragActive = false;

        currentLine.endXProperty().unbind();
        currentLine.endYProperty().unbind();
        root.getChildren().remove(currentLine);
        currentLine = null;
    }

    private void attachHandlers(Scene scene) {
        scene.setOnMouseMoved(e -> {
            mouseX.set(e.getSceneX());
            mouseY.set(e.getSceneY());
        });

        scene.setOnMouseDragged(e -> {
            mouseX.set(e.getSceneX());
            mouseY.set(e.getSceneY());
        });

        scene.setOnMousePressed(e -> {
            findNode(e.getSceneX(), e.getSceneY()).ifPresent(this::startDrag);
        });

        scene.setOnMouseReleased(e -> {
            Optional<Circle> maybeNode = findNode(e.getSceneX(), e.getSceneY());
            if (maybeNode.isPresent()) {
                stopDrag(maybeNode.get());
            } else {
                stopDrag();
            }
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());

        attachHandlers(scene);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}