package org.markadr;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * From https://gist.github.com/markadr
 * 
 * @author Mark de Reeper
 */
public class RotaryEncoder {

    private final GpioPinDigitalInput inputA;
    private final GpioPinDigitalInput inputB;
    private final GpioController gpio;

    private long encoderValue = 0;
    private int lastEncoded = 0;
    private boolean firstPass = true;
    
    private RotaryEncoderListener listener;
    
    // based on [lastEncoded][encoded] lookup
    private static final int stateTable[][]= {
        {0, 1, 1, -1},
        {-1, 0, 1, -1},
        {-1, 1, 0, -1},
        {-1, 1, 1, 0}
    };
    
    private static final Logger LOGGER = Logger.getLogger(RotaryEncoder.class.getName());
    
    
    public RotaryEncoder(Pin pinA, Pin pinB, long initalValue) {

        encoderValue = initalValue;
        gpio = GpioFactory.getInstance();

        inputA = gpio.provisionDigitalInputPin(pinA, "PinA", PinPullResistance.PULL_UP);
        inputB = gpio.provisionDigitalInputPin(pinB, "PinB", PinPullResistance.PULL_UP);
        
        GpioPinListenerDigital inputAListener = new GpioPinListenerDigital() {

            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpdsce) {
                
                int stateA = gpdsce.getState().getValue();
                int stateB = inputB.getState().getValue();
                calcEncoderValue(stateA, stateB);
                LOGGER.log(Level.FINE, "{0}{1} encodedValue: {2}", new Object[]{stateA, stateB, encoderValue});
            }
        };
        
        inputA.addListener(inputAListener);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                LOGGER.info("RotarySwitch: Shutting down....");
                if (gpio != null) {
                    gpio.removeAllListeners();
                    gpio.shutdown();
                }
            }
        });
        LOGGER.log(Level.INFO, "RotarySwitch initialised on pinA {0} and pinB {1}", 
                new String[]{pinA.getName(), pinB.getName()});
    }
        
    public long getValue() {
        return encoderValue;
    }
    
    public void setListener(RotaryEncoderListener listener) {
        this.listener = listener;
    }
    
    private void calcEncoderValue(int stateA, int stateB) {
        
        // converting the 2 pin value to single number to end up with 00, 01, 10 or 11
        int encoded = (stateA << 1) | stateB;
        
        if (firstPass) {
            firstPass = false;
        } else {
            // going up states, 01, 11
            // going down states 00, 10
            int state = stateTable[lastEncoded][encoded];
            encoderValue += state;
            if (listener != null) {
                if (state == -1) {
                    listener.down(encoderValue);
                }
                if (state == 1) {
                    listener.up(encoderValue);
                }
            }
        }
        
        lastEncoded = encoded;
    }
}
