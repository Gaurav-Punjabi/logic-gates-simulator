package BackEnd;

import javafx.scene.layout.AnchorPane;

/**
 * This class is the base class of all the components that are used to construct a logic circuit.
 * This class is created just to maintain a hierarchy of all the components and applying the principles of OOP.
 */
public class CircuitComponent extends AnchorPane {

    /*******************************************************************************************************************
 *                                                VARIABLE DECLARATION
     ******************************************************************************************************************/
    private boolean inputAValue;
    private boolean outputValue;
    private CircuitComponent inputANode;
    private CircuitComponent outputNode;
    private String outputType;


    /*******************************************************************************************************************
     *                                                CONSTRUCTOR
     ******************************************************************************************************************/
    /**
     * This is the primary constructor of the CircuitComponent.
     * @param inputAValue : the input status of the component.
     * @param outputValue : the output status of the component.
     */
    public CircuitComponent(final boolean inputAValue,
                            final boolean outputValue) {
        this.inputAValue = inputAValue;
        this.outputValue = outputValue;
    }

    /**
     * This is a secondary overloaded constructor of CircuitComponent.
     * @param inputAValue the input status of the Component.
     */
    public CircuitComponent(final boolean inputAValue) {
        this(inputAValue, false);
    }

    /**
     * This is the default constructor of the CircuitComponent.
     */
    public CircuitComponent() {
        this(false);
    }

    public boolean hasInputANode() {
        if(inputANode == null)
            return false;
        return true;
    }
    public boolean hasOutputNode() {
        if(outputNode == null)
            return false;
        return true;
    }


    /*******************************************************************************************************************
     *                                                  SETTERS
     ******************************************************************************************************************/
    public void setOutput(boolean output) {
        this.outputValue = output;
    }
    public void setInputA(boolean inputA) {
        this.inputAValue = inputA;
    }
    public void setInputANode(CircuitComponent inputANode) {
        this.inputANode = inputANode;
    }
    public void setOutputNode(CircuitComponent outputNode) {
        this.outputNode = outputNode;
    }
    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    /*******************************************************************************************************************
     *                                                  GETTERS
     ******************************************************************************************************************/
    public boolean getInputAValue() {
        return this.inputAValue;
    }
    public boolean getOutputValue() {
        return this.outputValue;
    }
    public CircuitComponent getInputANode() {
        return inputANode;
    }
    public CircuitComponent getOutputNode() {
        return outputNode;
    }
     public String getOutputType() {
        return outputType;
    }
}

