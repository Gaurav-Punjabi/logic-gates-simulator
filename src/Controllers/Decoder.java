package Controllers;

import BackEnd.BinaryGate;
import BackEnd.CircuitComponent;
import BackEnd.UnaryGate;
import UserInterface.components.*;

import java.util.ArrayList;
import java.util.List;

public class Decoder {
    private DragEventHandler ref;
    private List<PowerSourceComponent> powerSources;
    public Decoder(DragEventHandler ref) {
        this.ref = ref;
        this.powerSources = new ArrayList<>();
    }

    public boolean addPowerSource(final PowerSourceComponent powerSource) {
        if(powerSource == null)
            return false;
        if(powerSources.contains(powerSource))
            return false;
        return powerSources.add(powerSource);
    }

    public void decode() {
        for (PowerSourceComponent powerSourceComponent:
             powerSources) {
            parsePowerSource(powerSourceComponent);
        }
    }

    private void parsePowerSource(final CircuitComponent target) {
        if(target instanceof CircuitComponent) {
            CircuitComponent circuitComponent = (CircuitComponent)target;
            if(target instanceof UnaryGate) {
                UnaryGate unaryGate = (UnaryGate)target;
                boolean value = !unaryGate.getInput();
                unaryGate.setOutput(value);
            } else if(target instanceof BinaryGate) {
                BinaryGate binaryGate = (BinaryGate)target;
                boolean value = false;
                if(binaryGate instanceof AndGate)
                    value = binaryGate.getInputAValue() & binaryGate.getInputBValue();
                else if(binaryGate instanceof OrGateComponent)
                    value = binaryGate.getInputAValue() | binaryGate.getInputBValue();
                else if(binaryGate instanceof NandGateComponent)
                    value = !(binaryGate.getInputAValue() & binaryGate.getInputBValue());
                else if(binaryGate instanceof NorGateComponent)
                    value = !(binaryGate.getInputAValue() | binaryGate.getInputBValue());
                binaryGate.setOutput(value);
            }
            CircuitComponent outputNode = (CircuitComponent)circuitComponent.getOutputNode();
            if(outputNode instanceof LEDComponent) {
                LEDComponent ledComponent = (LEDComponent)outputNode;
                ledComponent.setLed(circuitComponent.getOutputValue());
            } else if(outputNode instanceof BinaryGate) {
                BinaryGate binaryGate = (BinaryGate)outputNode;
                if(circuitComponent.getOutputType().equals("A")) {
                    binaryGate.setInputA(circuitComponent.getOutputValue());
                } else if(circuitComponent.getOutputType().equals("B")) {
                    binaryGate.setInputBValue(circuitComponent.getOutputValue());
                }
                parsePowerSource(binaryGate);
            } else if(outputNode instanceof UnaryGate) {
                UnaryGate unaryGate = (UnaryGate)outputNode;
                unaryGate.setInput(circuitComponent.getOutputValue());
                parsePowerSource(unaryGate);
            }
        }
    }
}
