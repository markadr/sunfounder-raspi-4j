/**
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Marc Andreu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package org.mandfer.sunfunpi4j;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import static org.mandfer.sunfunpi4j.BaseSketch.logger;


/**
 * This example is an example to analyse the input events from the rotary encoder. 
 * 
 * The rotary encoder from Keyes is not compatible with listener or interruption events, due to 
 * the issue about missing state changes in between the waves.
 *
 * @author marcandreuf
 */
public class Ex09_RotaryEncoderListener extends BaseSketch {   

    private static GpioPinDigitalInput roAPin;
    private static GpioPinDigitalInput roBPin;
    
    private static final int stateTable[][]= {
        {0, 1, 2, -1},
        {-1, 0, 1, 2},
        {2, -1, 0, 1},
        {1, 2, -1, 0}
    };
    
    public Ex09_RotaryEncoderListener(GpioController gpio) {
        super(gpio);
    }
        
    public static void main(String[] args) throws InterruptedException {
        Ex09_RotaryEncoderListener ex09_rotaryEncoder = new Ex09_RotaryEncoderListener(GpioFactory.getInstance());
        ex09_rotaryEncoder.run(args);
    }

    @Override
    protected void setup() {
        roAPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP);
        roBPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_UP);
        roAPin.setDebounce(5);
        roBPin.addListener(new RotaryListener());
        logger.debug("Rotary encoder ready ");
    }   
    
    @Override
    protected void loop(String[] args) throws InterruptedException {        
        countDownLatchEndSketch.await();
    }    
   
    protected static class RotaryListener implements GpioPinListenerDigital {
        private int lastSeq = 0; 
        private int currentSeq = 0;
        private int direction = 0;
        
        public RotaryListener(){
            
        }
        
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpdsce) {
            currentSeq = calcSeq(roAPin.getState().getValue(), roBPin.getState().getValue());
            direction = stateTable[lastSeq][currentSeq];
            if(direction != 0){
                logger.debug(" ----------------- "+ gpdsce.toString()+" -----------------");
                logger.debug("Pin 0 "+roAPin.getState()+" Pin 1 "+roBPin.getState());
                logger.debug("Event pin "+gpdsce.getPin().getName()+" state "+gpdsce.getState());
                logger.debug("Sequence: "+currentSeq);
                logger.debug("Encoded: "+calcEncoded(roAPin.getState().getValue(), roBPin.getState().getValue()));
                logger.debug("Encoded State: "+rotaryEncoderState(roAPin.getState().getValue(), roBPin.getState().getValue()));
                logger.debug("Direction: "+stateTable[lastSeq][currentSeq]);
                logger.debug("----------------------------------------------------------");
                lastSeq = currentSeq;
            }
        }
               
    }
        
    
    public static int calcSeq(int a, int b){
        return (a ^ b) | b << 1;
    }
    
    public static int rotaryEncoderState(int a, int b){
        int c = a ^ b;
        return a*4 + b*2 + c*1; 
    }
           
    public static long calcDelta(int lastState, int newState){        
        return (newState - lastState) % 4;
    }
    
    public static int calcEncoded(int stateA, int stateB){
         return (stateA << 1) | stateB;
    }

}
