package Controllers;

import BackEnd.BinaryGate;
import BackEnd.CircuitComponent;
import BackEnd.PowerSource;
import Constants.*;
import UserInterface.components.*;
import UserInterface.DashboardController;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

/**
 * This is the main class where all the event handling for dragging the different components is handled.
 * The class's task are divided into 3 different parts :
 *      1- The dragEventHandling of the palette components
 *      2- The dragEventHandling of the main canvas
 *      3- The dragEventHandling of the lines.
 */
public class DragEventHandler implements Constants, DragConstants {

    /*******************************************************************************************************************
                                                    VARIABLE DECLARATION
     ******************************************************************************************************************/
    private DashboardController dashboardController;
    // this variable holds the current variable which is being dragged.
    private Node currentComponent;
    private AnchorPane sourceComponent;
    // this boolean is a flag that is used to indicate that a dragged component has been dropped and should'nt be removed
    // when the drag exits.
    private boolean componentDropped,newComponent;
    // Just storing the reference of the CanvasPane as it would be required frequently.
    private Pane canvasPanel;
    // The object of the decoder that decodes the circuit and generates the output
    private Decoder decoder;

    /*******************************************************************************************************************
                                                 END OF VARIABLE DECLARATION
     ******************************************************************************************************************/


    /*******************************************************************************************************************
                                                        CONSTRUCTOR
     ******************************************************************************************************************/
    public DragEventHandler(final DashboardController dashboardController){
        this.dashboardController  = dashboardController;
        this.currentComponent = null;
        this.componentDropped = false;
        this.newComponent = false;
        this.decoder = new Decoder(this);
        this.canvasPanel = dashboardController.getCanvasPanel();
        addListeners();
    }
    /*******************************************************************************************************************
                                                    END OF CONSTRUCTOR
     ******************************************************************************************************************/


    /**
     * This method just adds all the listeners to the respective components.
     * It wasn't necessary just did it to segregate the listeners from the rest of the code.
     */
    private void addListeners() {
        dashboardController.getAndGateButton().setOnDragDetected(event -> paletteDragDetected(event, TYPE_AND_GATE));
        dashboardController.getLEDButton().setOnDragDetected(event -> paletteDragDetected(event, TYPE_LED));
        dashboardController.getPowerSourceButton().setOnDragDetected(event -> paletteDragDetected(event, TYPE_POWER_SOURCE));
        dashboardController.getOrGateButton().setOnDragDetected(event -> paletteDragDetected(event, TYPE_OR_GATE));
        dashboardController.getNandGateButton().setOnDragDetected(event -> paletteDragDetected(event, TYPE_NAND_GATE));
        dashboardController.getNorButtonGate().setOnDragDetected(event -> paletteDragDetected(event, TYPE_NOR_GATE));
        dashboardController.getPowerButton().setOnMouseClicked(this::powerButtonClicked);
        dashboardController.getCanvasPanel().setOnMouseDragEntered(this::canvasDragEntered);
        dashboardController.getCanvasPanel().setOnMouseDragExited(this::canvasDragExited);
        dashboardController.getCanvasPanel().setOnMouseDragOver(this::canvasDragOver);
    }

    /**
     * This method is used to handle the dragDetected event on any component on the palette.
     * Whenever this event is generated a dragBoard is created with the content as the type of component.
     * The component type also needs to be passed so that it can determine which component's object needs to be created.
     * @param mouseEvent
     * @param componentType
     */
    public void paletteDragDetected(MouseEvent mouseEvent, String componentType) {
        ((Node)mouseEvent.getSource()).startFullDrag();
        this.currentComponent = generateRequiredComponent(componentType);
        this.newComponent = false;
    }

    /**
     * This method is used to handle the event when a object is dragged and it enters the Canvas Panel.
     * Since the DragEntered event is called many times hence we placed a flag that indicates whether the component actually
     * exited the canvas or not.
     * Also whenever the drag actually enters the CanvasPanel we set its co-ordinates and add it to the CanvasPanel.
     * @param dragEvent
     */
    public void canvasDragEntered(MouseDragEvent dragEvent) {
        if(!this.canvasPanel.getChildren().contains(currentComponent))
            this.canvasPanel.getChildren().add(currentComponent);
        if(this.currentComponent instanceof PowerSourceComponent)
            this.decoder.addPowerSource((PowerSourceComponent)this.currentComponent);
    }

    /**
     * This method is used to handle the dragOver event on the CanvasPanel.
     * We just simply change the position of the component that was dragged.
     * @param mouseEvent
     */
    public void canvasDragOver(MouseEvent mouseEvent) {
        this.setPosition(mouseEvent,currentComponent);
    }

    private void powerButtonClicked(MouseEvent mouseEvent) {
        this.decoder.decode();
    }


    /**
     * This method is used to handle event which is generated when a drag exits the CanvasPanel.
     * Now when a object exits the bounds of canvas we want to remove it from the canvas.
     * But there are 2 conditions when a drag exits the Canvas :
     *      1 - When the object actually exits the bounds of the Canvas.
     *      2 - When the object is dropped.
     * So as we don't want to remove the component every time the exit is called hence we created a flag - componentDropped
     * which indicates whether the object was dropped or it actually exited the Canvas.
     * @param dragEvent
     */
    public void canvasDragExited(MouseDragEvent dragEvent) {
        if(!newComponent)
        addListenersToComponent((AnchorPane) currentComponent);
        this.newComponent = false;
        this.currentComponent = null;
    }

    /**
     * This method is used to handle the event when a component on the canvas is dragged.
     * Now we don't have reference to the actual component which needs to be moved we have the reference of the ImageView
     * inside that component so we simply use the getParent method to access the component.
     * In this we just set the currentComponent to the component that needs to moved.
     * @param mouseEvent
     */
    public void componentDragDetected(MouseEvent mouseEvent) {
        Object object = mouseEvent.getSource();
        if(object instanceof Node) {
            Node component = ((Node)object);
            getNodeParent(component).startFullDrag();
            System.out.println("componentDragDetected checking the Parent node : " + getNodeParent(component));
            this.currentComponent = component.getParent().getParent();
            this.newComponent = true;
        }
    }

    public void outputDragDetected(MouseEvent mouseEvent) {
        Node component = ((Node)mouseEvent.getSource());
        Dragboard dragboard = component.getParent().getParent().startDragAndDrop(TransferMode.MOVE);
        ClipboardContent clipboardContent = new ClipboardContent();

        double x = component.getLayoutX() + component.getParent().getParent().getLayoutX() + component.getLayoutBounds().getWidth()/2;
        double y = component.getLayoutY() + component.getParent().getParent().getLayoutY() + component.getLayoutBounds().getHeight()/2;

        this.currentComponent = createLine(x,y,x,y);
        this.sourceComponent = (AnchorPane)getNodeParent(mouseEvent.getSource());

        mapLineTo(getNodeParent(mouseEvent.getSource()),(Line)this.currentComponent, ((Node) mouseEvent.getSource()).getId());

        // just adjusting the UX for the user
        Object object = mouseEvent.getSource();
        if(object instanceof ImageView)
            ((ImageView)object).setImage(new Image("resources/icons/connector-positive.png"));

        clipboardContent.putString("String");
        dragboard.setContent(clipboardContent);
        setDragView(dragboard);
    }

    public void inputDragEntered(DragEvent dragEvent) {
        if(this.currentComponent instanceof Line) {
            // setting the position of the line by passing the inputConnector and the line component.
            setLinePosition((Node)dragEvent.getSource(), (Line)this.currentComponent);

            // checking if the component is present or not, if not then adding the component to the canvas.
            if(!this.canvasPanel.getChildren().contains(this.currentComponent))
                this.canvasPanel.getChildren().add(this.currentComponent);
        }
    }

    public void inputDragOver(DragEvent dragEvent) {
        if(this.currentComponent instanceof Line) {
            mapLineTo(getNodeParent(dragEvent.getSource()), (Line)this.currentComponent, ((Node)dragEvent.getSource()).getId());
            System.out.println("InputDragOver checking FXID : " + ((Node) dragEvent.getSource()).getId());
            mapNodeTo(sourceComponent,getNodeParent(dragEvent.getSource()),((Node) dragEvent.getSource()).getId());

            // just making the UX better
            Object object = dragEvent.getSource();
            if(object instanceof ImageView) {
                ((ImageView)object).setImage(new Image("resources/icons/connector-positive.png"));
            }
            ((Line)this.currentComponent).setStroke(Color.web("#33E47F"));

            // setting the position of the line
            setLinePosition((Node)dragEvent.getSource(),(Line)this.currentComponent);
            if(!this.canvasPanel.getChildren().contains(this.currentComponent))
                this.canvasPanel.getChildren().add(this.currentComponent);
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        }
    }

    public void inputDragExited(DragEvent dragEvent) {
    }


     //                                             SERVICE METHODS
     /**
      * This method is used to set the position of the component.
      * We take the current X and Y position of the mouse and subtract it by half of the width and height of the component
      * as we need to drag it from the center.
      * @param mouseEvent : the mouseEvent from which mouse co-ordinates can be accessed.
      * @param node : the object that needs to be positioned.
      */
    private void setPosition(final MouseEvent mouseEvent,
                             final Node node) {
        if(node instanceof AnchorPane) {
            AnchorPane anchorPane = (AnchorPane)node;
            double layoutX = mouseEvent.getX() - anchorPane.getWidth() / 2;
            double layoutY = mouseEvent.getY() - anchorPane.getHeight() / 2;
            anchorPane.setLayoutX(layoutX);
            anchorPane.setLayoutY(layoutY);
            if(node instanceof LEDComponent) {
                LEDComponent ledComponent = (LEDComponent)node;
                adjustLine(ledComponent.getInputLine(),layoutX,layoutY,ledComponent.getInputImage(),true);
            } else if(node instanceof AndGate) {
                AndGate andGate = (AndGate)node;
                adjustLine(andGate.getOutputLine(),layoutX,layoutY,andGate.getOutputImage(),false);
                adjustLine(andGate.getInputALine(),layoutX,layoutY,andGate.getInputAImage(),true);
                adjustLine(andGate.getInputBLine(),layoutX,layoutY,andGate.getInputBImage(),true);
            } else if(node instanceof OrGateComponent) {
                OrGateComponent orGateComponent = (OrGateComponent)node;
                adjustLine(orGateComponent.getOutputLine(),layoutX,layoutY,orGateComponent.getOutputConnector(),false);
                adjustLine(orGateComponent.getInputALine(),layoutX, layoutY,orGateComponent.getInputAImage(),true);
                adjustLine(orGateComponent.getInputBLine(),layoutX,layoutY,orGateComponent.getInputBImage(),true);
            } else if(node instanceof NandGateComponent){
                NandGateComponent nandGateComponent = (NandGateComponent) node;
                adjustLine(nandGateComponent.getOutputLine(),layoutX,layoutY,nandGateComponent.getOutputImage(),false);
                adjustLine(nandGateComponent.getInputALine(),layoutX,layoutY,nandGateComponent.getInputAImage(),true);
                adjustLine(nandGateComponent.getInputBLine(),layoutX,layoutY,nandGateComponent.getInputBImage(),true);
            } else if(node instanceof PowerSourceComponent) {
                PowerSourceComponent powerSourceComponent = (PowerSourceComponent)node;
                adjustLine(powerSourceComponent.getOutputLine(),layoutX,layoutY,powerSourceComponent.getOutputConnector(),false);
            } else if(node instanceof NorGateComponent) {
                NorGateComponent norGateComponent = (NorGateComponent) node;
                adjustLine(norGateComponent.getOutputLine(),layoutX,layoutY,norGateComponent.getOutputImage(),false);
                adjustLine(norGateComponent.getInputALine(),layoutX,layoutY,norGateComponent.getInputAImage(),true);
                adjustLine(norGateComponent.getInputBLine(),layoutX,layoutY,norGateComponent.getInputBImage(),true);
            }
        }
    }

    private void adjustLine(final Line lineToAdjust,
                            final double x,
                            final double y,
                            final ImageView targetToAdjustWith,
                            final boolean isEnd) {
        if(lineToAdjust == null)
            return;
        if(isEnd) {
            lineToAdjust.setEndX(x + targetToAdjustWith.getLayoutX() + targetToAdjustWith.getLayoutBounds().getWidth()/2);
            lineToAdjust.setEndY(y + targetToAdjustWith.getLayoutY() + targetToAdjustWith.getLayoutBounds().getHeight()/2);
        } else {
            lineToAdjust.setStartX(x + targetToAdjustWith.getLayoutX() + targetToAdjustWith.getLayoutBounds().getWidth()/2);
            lineToAdjust.setStartY(y + targetToAdjustWith.getLayoutY() + targetToAdjustWith.getLayoutBounds().getHeight()/2);
        }
    }

    private void setDragView(Dragboard dragboard) {
        dragboard.setDragView(new Image("resources/icons/drag-image.png"));
    }

    private void addListenersToComponent(AnchorPane currentComponent) {
        if(currentComponent == null) {
            return;
        }
        System.out.println("Listeners Added");
        if(currentComponent instanceof AndGate) {
            AndGate andGate = (AndGate)currentComponent;
            andGate.getAndGate().setOnDragDetected(this::componentDragDetected);
            andGate.getOutputImage().setOnDragDetected(this::outputDragDetected);
            andGate.getInputAImage().setOnDragOver(this::inputDragOver);
            andGate.getInputAImage().setOnDragEntered(this::inputDragEntered);
            andGate.getInputAImage().setOnDragExited(this::inputDragExited);
            andGate.getInputBImage().setOnDragOver(this::inputDragOver);
            andGate.getInputBImage().setOnDragEntered(this::inputDragEntered);
            andGate.getInputBImage().setOnDragExited(this::inputDragExited);
        } else if(currentComponent instanceof OrGateComponent) {
            OrGateComponent orGateComponent = (OrGateComponent)currentComponent;
            orGateComponent.getOrGate().setOnDragDetected(this::componentDragDetected);
            orGateComponent.getOutputConnector().setOnDragDetected(this::outputDragDetected);
            orGateComponent.getInputAImage().setOnDragEntered(this::inputDragEntered);
            orGateComponent.getInputAImage().setOnDragOver(this::inputDragOver);
            orGateComponent.getInputAImage().setOnDragExited(this::inputDragExited);
            orGateComponent.getInputBImage().setOnDragEntered(this::inputDragEntered);
            orGateComponent.getInputBImage().setOnDragExited(this::inputDragExited);
            orGateComponent.getInputBImage().setOnDragOver(this::inputDragOver);
        }else if(currentComponent instanceof LEDComponent) {
            LEDComponent ledComponent = (LEDComponent)currentComponent;
            ledComponent.getLEDImage().setOnDragDetected(this::componentDragDetected);
            ledComponent.getInputImage().setOnDragEntered(this::inputDragEntered);
            ledComponent.getInputImage().setOnDragOver(this::inputDragOver);
            ledComponent.getInputImage().setOnDragExited(this::inputDragExited);
        } else if(currentComponent instanceof NandGateComponent) {
            NandGateComponent nandGateComponent = (NandGateComponent) currentComponent;
            nandGateComponent.getNandGate().setOnDragDetected(this::componentDragDetected);
            nandGateComponent.getOutputImage().setOnDragDetected(this::outputDragDetected);
            nandGateComponent.getInputAImage().setOnDragOver(this::inputDragOver);
            nandGateComponent.getInputAImage().setOnDragEntered(this::inputDragEntered);
            nandGateComponent.getInputAImage().setOnDragExited(this::inputDragExited);
            nandGateComponent.getInputBImage().setOnDragOver(this::inputDragOver);
            nandGateComponent.getInputBImage().setOnDragEntered(this::inputDragEntered);
            nandGateComponent.getInputBImage().setOnDragExited(this::inputDragExited);
        } else if(currentComponent instanceof PowerSourceComponent) {
            PowerSourceComponent powerSourceComponent = (PowerSourceComponent)currentComponent;
            powerSourceComponent.getPowerSourceImage().setOnDragDetected(this::componentDragDetected);
            powerSourceComponent.getOutputConnector().setOnDragDetected(this::outputDragDetected);
        } else if(currentComponent instanceof NorGateComponent) {
            NorGateComponent norGateComponent = (NorGateComponent) currentComponent;
            norGateComponent.getNorGate().setOnDragDetected(this::componentDragDetected);
            norGateComponent.getOutputImage().setOnDragDetected(this::outputDragDetected);
            norGateComponent.getInputAImage().setOnDragOver(this::inputDragOver);
            norGateComponent.getInputAImage().setOnDragEntered(this::inputDragEntered);
            norGateComponent.getInputAImage().setOnDragExited(this::inputDragExited);
            norGateComponent.getInputBImage().setOnDragOver(this::inputDragOver);
            norGateComponent.getInputBImage().setOnDragEntered(this::inputDragEntered);
            norGateComponent.getInputBImage().setOnDragExited(this::inputDragExited);
        }
    }

    private  Line createLine(final double startX,
                             final double startY,
                             final double endX,
                             final double endY) {
        Line line = new Line(startX,startY,endX,endY);
        line.setStrokeWidth(3);
        line.setStroke(LINE_COLOR);
        return line;
    }

    private void setLinePosition(final Node target,
                                 final Line line) {

        double x = target.getLayoutX() + target.getParent().getParent().getLayoutX() + target.getLayoutBounds().getWidth()/2;
        double y = target.getLayoutY() + target.getParent().getParent().getLayoutY() + target.getLayoutBounds().getHeight()/2;

        line.setEndX(x);
        line.setEndY(y);
    }

    private void mapLineTo(final Object target,
                           final Line line,
                           final String fxID) {
        if(target instanceof LEDComponent) {
            LEDComponent ledComponent = (LEDComponent) target;
            if(ledComponent.getInputLine() != null)
                this.canvasPanel.getChildren().remove(ledComponent.getInputLine());
            ledComponent.setInputLine(line);
        } else if(target instanceof BinaryGate) {
            BinaryGate binaryGate = (BinaryGate) target;
            switch (fxID) {
                case "inputA" :
                    if(binaryGate.getInputALine() != null)
                        this.canvasPanel.getChildren().remove(binaryGate.getInputALine());
                    binaryGate.setInputALine(line);
                    break;

                case "inputB" :
                    if(binaryGate.getInputBLine() != null)
                        this.canvasPanel.getChildren().remove(binaryGate.getInputBLine());
                    binaryGate.setInputBLine(line);
                    break;

                case "outputConnector" :
                    if(binaryGate.getOutputLine() != null)
                        this.canvasPanel.getChildren().remove(binaryGate.getOutputLine());
                    binaryGate.setOutputLine(line);
                    break;
            }
        } else if(target instanceof PowerSource) {
            PowerSource powerSource = (PowerSource)target;
            if(powerSource.getOutputLine() != null)
                this.canvasPanel.getChildren().remove(powerSource.getOutputLine());
            powerSource.setOutputLine(line);
        }
    }

    public void mapNodeTo(final Parent outputNode,
                          final Parent inputNode,
                          final String fxId) {
        System.out.println("Checkign the fxID : " + fxId);
        if(outputNode instanceof LEDComponent ||
                inputNode instanceof PowerSourceComponent ||
                outputNode == null ||
                inputNode == null  ||
                !(outputNode instanceof CircuitComponent) ||
                !(inputNode instanceof CircuitComponent)) {
            return;
        }
        CircuitComponent outputComponent = (CircuitComponent)outputNode;
        ((CircuitComponent) outputNode).setOutputNode((CircuitComponent) inputNode);
        if(inputNode instanceof BinaryGate) {
            BinaryGate binaryGate = (BinaryGate)inputNode;
            if(fxId.equals("inputA")) {
                binaryGate.setInputANode(outputComponent);
                outputComponent.setOutputType("A");
            } else if(fxId.equals("inputB")) {
                binaryGate.setInputBNode(outputComponent);
                outputComponent.setOutputType("B");
            }
        } else if(inputNode instanceof LEDComponent) {
            LEDComponent ledComponent = (LEDComponent)inputNode;
            ledComponent.setInputANode(outputComponent);
            outputComponent.setOutputType("A");
        }
    }

    private Parent getNodeParent(Object object) {
        return ((Node)object).getParent().getParent();
    }

    private Node generateRequiredComponent(String componentType) {
        switch (componentType) {
            case TYPE_AND_GATE :
                return new AndGate();

            case TYPE_LED:
                return new LEDComponent();

            case TYPE_POWER_SOURCE:
                return new PowerSourceComponent();

            case TYPE_OR_GATE:
                return new OrGateComponent();

            case TYPE_NAND_GATE:
                return new NandGateComponent();

            case TYPE_NOR_GATE:
                return new NorGateComponent();

            default:
                return null;
        }
    }
}
