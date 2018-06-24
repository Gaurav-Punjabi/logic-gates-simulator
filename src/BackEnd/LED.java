package BackEnd;

import javafx.scene.shape.Line;

/**
 * This is just a Wrapper class for the LED it just stores the inputLine that is being connected to its inputNode.
 */
public class LED extends CircuitComponent{
    /*******************************************************************************************************************
     *                                              VARIABLE DECLARATION
     ******************************************************************************************************************/
    //The Line connected to the inputNode of the LED Light.
    private Line inputLine;


    /*******************************************************************************************************************
     *                                                CONSTRUCTOR
     ******************************************************************************************************************/
    /**
     * This is the primary constructor that inputs whether the LED should be on or off.
     * @param input
     */
    public LED(final boolean input) {
        super(input);
    }

    /**
     * This is the default constructor of this object.
     */
    public LED() {
        this(false);
    }


    public void toggleInput() {
        setInputA(!getInputAValue());
    }

    /*******************************************************************************************************************
     *                                                    SETTERS
     ******************************************************************************************************************/
    /**
     * It sets the line connected to the input node of the LED.
     * @param inputLine : line object connected to the input node.
     */
    public void setInputLine(Line inputLine) {
        this.inputLine = inputLine;
    }

    /*******************************************************************************************************************
     *                                                   GETTERS
     ******************************************************************************************************************/
    /**
     * It returns the line object connected to the input node of the LED.
     * @return the line object connected to input node.
     */
    public Line getInputLine() {
        return inputLine;
    }
}
