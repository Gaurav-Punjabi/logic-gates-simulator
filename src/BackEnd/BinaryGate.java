package BackEnd;

import javafx.scene.shape.Line;

/**
 * This is the wrapper of all the binary gates(the gates with 2 inputs)
 * This class stores all the type of gates.
 */
public class BinaryGate extends CircuitComponent implements GateConstants {
    /*******************************************************************************************************************
     *                                              VARIABLE DECLARATION
     ******************************************************************************************************************/
    // Stores the result of the second input connected to this gate.
    private boolean inputBValue;
    // This stores the reference of the component connected to the output of this component.
    private CircuitComponent inputBNode;
    // This stores the object of the Line connected to the first and second input of this object.
    private Line inputALine, inputBLine;
    //This stores the object of the connected to the output of this object.
    private Line outputLine;
    // This stores the type of gate that would be required.
    private int gateType;

    /*******************************************************************************************************************
     *                                                CONSTRUCTOR
     ******************************************************************************************************************/
    /**
     * This is the primary constructor of the class that initializes all the data members of the class.
     * @param inputAValue the status of the first input node.
     * @param inputBValue the status of the second input node.
     * @param output the status of the output node.
     */
    public BinaryGate(final boolean inputAValue,
                      final boolean inputBValue,
                      final boolean output,
                      final int gateType) {
        super(inputAValue,output);
        this.inputBValue = inputBValue;
        this.gateType = gateType;
    }
    /**
     * The secondary overloaded constructor of this object.
     * @param inputAValue the status of the input at the first node.
     */
    public BinaryGate(final boolean inputAValue) {
        this(inputAValue, false, false,AND_GATE);
    }

    public BinaryGate(final int gateType) {
        this(false, false, false, gateType);
    }
    /**
     * The default constructor of the class.
     */
    public BinaryGate() {
        this(false, false, false,AND_GATE);
    }

    public boolean hasInputBNode() {
        if(inputBNode == null)
            return false;
        return true;
    }

    /*******************************************************************************************************************
     *                                                   SETTERS
     ******************************************************************************************************************/
    public void setInputBValue(boolean inputBValue) {
        this.inputBValue = inputBValue;
    }
    public void setInputALine(Line inputALine) {
        this.inputALine = inputALine;
    }
    public void setInputBLine(Line inputBLine) {
        this.inputBLine = inputBLine;
    }
    public void setOutputLine(Line outputLine) {
        this.outputLine = outputLine;
    }
    public void setGateType(int gateType) {
        this.gateType = gateType;
    }
    public void setInputBNode(CircuitComponent inputBNode) {
        this.inputBNode = inputBNode;
    }

    /*******************************************************************************************************************
     *                                                   GETTERS
     ******************************************************************************************************************/
    public Line getOutputLine() {
        return outputLine;
    }
    public Line getInputALine() {
        return inputALine;
    }
    public Line getInputBLine() {
        return inputBLine;
    }
    public int getGateType() {
        return gateType;
    }
    public boolean getInputBValue() {
        return this.inputBValue;
    }
    public CircuitComponent getInputBNode() {
        return inputBNode;
    }
}
