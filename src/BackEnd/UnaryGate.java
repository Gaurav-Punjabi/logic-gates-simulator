package BackEnd;

import javafx.scene.shape.Line;

public class UnaryGate extends CircuitComponent implements GateConstants{
    /*******************************************************************************************************************
     *                                              VARIABLE DECLARATION
     ******************************************************************************************************************/
    // Stores the object of the line connected to output node of this object.
    private Line outputLine;
    // Store the object of the line connected to input node of this object.
    private Line inputLine;
    // Stores the information about which type of gate is this object acting as.
    private int gateType;

    /*******************************************************************************************************************
     *                                                CONSTRUCTOR
     ******************************************************************************************************************/
    public UnaryGate(final boolean input,
                     final boolean output,
                     final int gateType) {
        super(input, output);
        this.gateType = gateType;
    }
    public UnaryGate(final int gateType) {
        this(false, false, gateType);
    }
    public UnaryGate() {
        this(NOT_GATE);
    }

    /*******************************************************************************************************************
     *                                                  SETTERS
     ******************************************************************************************************************/
    public void setOutputLine(Line outputLine) {
        this.outputLine = outputLine;
    }
    public void setInputLine(Line inputLine) {
        this.inputLine = inputLine;
    }
    public void setGateType(int gateType) {
        this.gateType = gateType;
    }
    public void setInput(boolean input) {
        super.setInputA(input);
    }


    /*******************************************************************************************************************
     *                                                 GETTERS
     ******************************************************************************************************************/
    public int getGateType() {
        return gateType;
    }
    public Line getOutputLine() {
        return outputLine;
    }
    public Line getInputLine() {
        return inputLine;
    }
    public boolean getInput() {
        return super.getInputAValue();
    }
}
