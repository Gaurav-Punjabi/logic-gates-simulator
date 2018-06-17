package Controllers;

import Constants.Constants;
import UserInterface.components.AndGate;
import UserInterface.DashboardController;
import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

/**
 * This is the main class where all the event handling for dragging the different components is handled.
 * The class's task are divided into 3 different parts :
 *      1- The dragEventHandling of the palette components
 *      2- The dragEventHandling of the main canvas
 *      3- The dragEventHandling of the lines.
 */
public class DragEventHandler implements Constants {

    //                                      VARIABLE DECLARATION
    private DashboardController dashboardController;
    // this variable holds the current variable which is being dragged
    private AnchorPane currentComponent;
    // this boolean is a flag that is used to indicate that a dragged component has entered the canvas.
    private boolean componentEntered;
    // this boolean is a flag that is used to indicate that a dragged component has been dropped and should'nt be removed
    // when the drag exits.
    private boolean componentDropped;
    //                                    END OF VARIABLE DECLARATION

    //                                            CONSTRUCTOR
    public DragEventHandler(final DashboardController dashboardController){
        this.dashboardController  = dashboardController;
        this.currentComponent = null;
        this.componentEntered = false;
        this.componentDropped = false;
        addListeners();
    }

    /**
     * This method just adds all the listeners to the respective components.
     * It was'nt necessary just did it to segregate the listeners from the rest of the code.
     */
    private void addListeners() {
        dashboardController.getAndGateButton().setOnDragDetected(event -> paletteDragDetected(event, TYPE_AND_GATE));
//        dashboardController.getOrGateButton().setOnDragDetected(event -> paletteDragDetected(event, TYPE_OR_GATE));
//        dashboardController.getNotGateButton().setOnDragDetected(event -> paletteDragDetected(event, TYPE_NOT_GATE));
//        dashboardController.getNandGateButton().setOnDragDetected(event -> paletteDragDetected(event, TYPE_NAND_GATE));
//        dashboardController.getNorButtonGate().setOnDragDetected(event -> paletteDragDetected(event, TYPE_NOR_GATE));
//        dashboardController.getXorGateButton().setOnDragDetected(event -> paletteDragDetected(event, TYPE_XOR_GATE));
//        dashboardController.getXnorGateButton().setOnDragDetected(event -> paletteDragDetected(event, TYPE_XNOR_GATE));
        dashboardController.getCanvasPanel().setOnDragEntered(this::canvasDragEntered);
        dashboardController.getCanvasPanel().setOnDragOver(this::canvasDragOver);
        dashboardController.getCanvasPanel().setOnDragExited(this::canvasDragExited);
        dashboardController.getCanvasPanel().setOnDragDropped(this::canvasDragDropped);
    }

    /**
     * This method is used to handle the dragDetected event on any component on the palette.
     * Whenever this event is generated a dragboard is created with the content as the type of component.
     * The component type also needs to be passed so that it can determine which component's object needs to be created.
     * @param mouseEvent
     * @param componentType
     */
    public void paletteDragDetected(MouseEvent mouseEvent, String componentType) {
        // Starting the drag Event for the Canvas Panel.
        Dragboard dragboard = dashboardController.getCanvasPanel().startDragAndDrop(TransferMode.COPY_OR_MOVE);
        ClipboardContent clipboardContent = new ClipboardContent();
        // Adding the string as componentType to the clipBoard.
        clipboardContent.putString(componentType);
        //Checking for which type of component needs to be initialized.
        switch (componentType) {
            case TYPE_AND_GATE :
                this.currentComponent = new AndGate();
                break;

            case TYPE_OR_GATE :
                System.out.println("Or Gate Selected.");
                break;

            case TYPE_NOT_GATE:
                System.out.println("Not gate Selected.");
                break;

            case TYPE_NAND_GATE:
                System.out.println("Nand Gate selected.");
                break;

            case TYPE_NOR_GATE:
                System.out.println("Nor gate selected.");
                break;

            case TYPE_XOR_GATE:
                System.out.println("Xor gate Selected.");
                break;

            case TYPE_XNOR_GATE:
                System.out.println("Xnor gate Seleceted.");
                break;
        }
        // Adding the clipboard to the dragBoard so that it can be transferred.
        dragboard.setContent(clipboardContent);
        mouseEvent.consume();
    }

    /**
     * This method is used to handle the event when a object is dragged and it enters the Canvas Panel.
     * Since the DragEntered event is called many times hence we placed a flag that indicates whether the component actually
     * exited the canvas or not.
     * Also whenever the drag actually enters the CanvasPanel we set its co-ordinates and add it to the CanvasPanel.
     * @param dragEvent
     */
    public void canvasDragEntered(DragEvent dragEvent) {
        //Checking for the flag.
        if(!componentEntered) {
            // Setting the position of the currentComponent.
            this.setPosition(dragEvent, currentComponent);
            //Checking if the component is already present or not.
            //If it is already present then we do not add it to the CanvasPanel.
            if(!this.dashboardController.getCanvasPanel().getChildren().contains(currentComponent))
                this.dashboardController.getCanvasPanel().getChildren().add(currentComponent);
            // Indicating that the drag actually entered the CanvasPanel.
            this.componentEntered = true;
        }
    }

    /**
     * This method is used to handle the dragOver event on the CanvasPanel.
     * We just simply change the position of the component that was dragged.
     * @param dragEvent
     */
    public void canvasDragOver(DragEvent dragEvent) {
        if(dragEvent.getDragboard().hasString() &&
            dragEvent.getSource() instanceof AnchorPane){
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            this.setPosition(dragEvent, currentComponent);
        }
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
    public void canvasDragExited(DragEvent dragEvent) {
        //Checking whether the object was just dropped or exited the bounds of Canvas.
        // if the component was dropped we do noting, we just reset the flags.
        if(componentDropped) {
            this.componentDropped = false;
            this.componentEntered = false;
        }
        //if the component exited the bounds of Canvas then we remove the component from the Canvas and reset the
        // entered flag.
        else if(componentEntered) {
            this.dashboardController.getCanvasPanel().getChildren().remove(currentComponent);
            this.componentEntered = false;
        }
    }

    /**
     * This method is used to handle the event which is generated when the object is dropped on the canvas.
     * When a object is dropped on the canvas we indicate that the component was dropped and add the listeners to the
     * components and reset the currentComponent to null.
     * @param dragEvent
     */
    public void canvasDragDropped(DragEvent dragEvent) {
        this.componentDropped = true;
        addAppropirateListeners(this.currentComponent);
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
        // Accessing the reference to the component that needs to be moved.
        AnchorPane sourceComponent = (AnchorPane) ((Node)mouseEvent.getSource()).getParent();
        Dragboard dragboard = sourceComponent.startDragAndDrop(TransferMode.COPY_OR_MOVE);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("COMPONENT");
        dragboard.setContent(clipboardContent);
        this.currentComponent = sourceComponent;
        mouseEvent.consume();
    }
    public void lineDragDetected(MouseEvent mouseEvent) {}
    public void lineDragOver(DragEvent dragEvent) {}
    public void lineDragDropped(DragEvent dragEvent) {}


     //                                             SERVICE METHODS
     /**
      * This method is used to set the position of the component.
      * We take the current X and Y position of the mouse and subtract it by half of the width and height of the component
      * as we need to drag it from the center.
      * @param dragEvent : the dragEvent from which mouse co-ordinates can be accessed.
      * @param anchorPane : the object that needs to be positioned.
      */
    private void setPosition(DragEvent dragEvent, AnchorPane anchorPane) {
        double layoutX = dragEvent.getX() - anchorPane.getWidth()/2;
        double layoutY = dragEvent.getY() - anchorPane.getHeight()/2;
        anchorPane.setLayoutX(layoutX);
        anchorPane.setLayoutY(layoutY);
    }

    /**
     * This method is used to add listeners to all the components of the given Node.
     * First the method identifies which type of component is It and then it adds the respective components.
     * @param component : The component to which listeners needs to be added.
     */
    private void addAppropirateListeners(Node component) {
        if(component == null)
            return;;
        if(component instanceof  AndGate) {
            AndGate ref = (AndGate) component;
            ref.getAndGate().setOnDragDetected(event -> componentDragDetected(event));
        }
    }
}