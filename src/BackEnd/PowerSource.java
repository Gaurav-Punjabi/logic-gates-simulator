package BackEnd;

import javafx.scene.shape.Line;


/**
 * This is the class that represents the PowerSource Components.
 * This class is basically the wrapper to the values of PowerSource.
 */
public class PowerSource extends CircuitComponent {

    /*******************************************************************************************************************
     *                                               VARIABLE DECLARATION
     ******************************************************************************************************************/
    // This stores the output Line (Actual Line shape object).
    private Line outputLine;
    //The next node connected to this powerSource.
    private CircuitComponent nextComponent;

    /*******************************************************************************************************************
     *                                                  CONSTRUCTOR
     ******************************************************************************************************************/
    /**
     * This is the primary constructor of the class that basically inputs whether the power provided needs to be +VCC or
     * -GND and then it calls the super constructor.
     * @param output the value at output node.
     */
    public PowerSource(final boolean output) {
        super(false,output);
    }

    /**
     * This is the default constructor of the class.
     * It just simply initializes the member variables.
     */
    public PowerSource() {
        this(false);
    }

    public void togglePower() {
        setOutput(!getOutputValue());
    }

    /*******************************************************************************************************************
     *                                                  SETTERS
     ******************************************************************************************************************/
    /**
     * Sets the Output Line from the powerSource to another component.
     * @param outputLine the object of the line that is connected to the output node.
     */
    public void setOutputLine(Line outputLine) {
        this.outputLine = outputLine;
    }

    /**
     * This sets the next component connected to the power source.
     * @param nextComponent the next circuit component.
     */
    public void setNextComponent(CircuitComponent nextComponent) {
        this.nextComponent = nextComponent;
    }


    /*******************************************************************************************************************
     *                                                  GETTERS
     ******************************************************************************************************************/
    /**
     * Returns the object of the line connected to output Node.
     * @return line object connected to the outputNode.
     */
    public Line getOutputLine() {
        return outputLine;
    }

    /**
     * This returns the nextComponent connected to the powerSource.
     * @return next component connected to the power source.
     */
    public CircuitComponent getNextComponent() {
        return nextComponent;
    }
}
