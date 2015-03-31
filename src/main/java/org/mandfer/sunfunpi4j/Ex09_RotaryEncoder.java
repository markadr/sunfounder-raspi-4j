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
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import static org.mandfer.sunfunpi4j.BaseSketch.logger;
import org.markadr.RotaryEncoder;
import org.markadr.RotaryEncoderListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This example is quite different from the original C code due to the use of 
 * Listeners which are much better option than pulling constantly the pin state.
 *
 * @author marcandreuf
 */
public class Ex09_RotaryEncoder extends BaseSketch {   

    private static GpioPinDigitalInput roAPin;
    private static GpioPinDigitalInput roBPin;
    
    public Ex09_RotaryEncoder(GpioController gpio) {
        super(gpio);
    }
        
    public static void main(String[] args) throws InterruptedException {
        Ex09_RotaryEncoder ex09_rotaryEncoder = new Ex09_RotaryEncoder(GpioFactory.getInstance());
        ex09_rotaryEncoder.run(args);
    }

    @Override
    protected void setup() {
        roAPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);
        roBPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01);
        
//        RotaryEncoder rotaryEncoder = new RotaryEncoder(RaspiPin.GPIO_00, RaspiPin.GPIO_01, 0);
//        
//        rotaryEncoder.setListener(new RotaryEncoderListener() {
//
//            @Override
//            public void up(long encoderValue) {
//                logger.debug("Up value : "+encoderValue);
//            }
//
//            @Override
//            public void down(long encoderValue) {
//                logger.debug("Down value : "+encoderValue);
//            }
//        });
        
        
        
        roBPin.addListener(new RotaryListener(new ConsoleCounter()));
        
        logger.debug("Rotary encoder ready ");
    }
    
    
    
    @Override
    protected void loop(String[] args) throws InterruptedException {        
//        int globalCounter = 0;
//        boolean flag = false;
//        PinState lastRoBStatus;
//        PinState currentRoBStatus=PinState.LOW;
        do{
//            lastRoBStatus = roBPin.getState();
//            
//            while(roAPin.isLow()){
//                currentRoBStatus = roBPin.getState();
//                flag=true;
//            }
//            
//            if(flag){
//                flag=false;
//                if(lastRoBStatus==PinState.LOW && currentRoBStatus==PinState.HIGH){
//                    globalCounter ++;
//                }
//                if(lastRoBStatus==PinState.HIGH && currentRoBStatus==PinState.LOW){
//                    globalCounter --;
//                }
//            }
//            
//            logger.debug("globalCounter : "+globalCounter);
        }while(isNotInterrupted);
    }
    
    protected static class ConsoleCounter {
        private final AtomicInteger counter;
        
        public ConsoleCounter(){
            counter = new AtomicInteger();
        }
        
        public void increment(){
            logger.info("Counter = "+counter.incrementAndGet());
        }
        
        public void decrement(){
            logger.info("Counter = "+counter.decrementAndGet());
        }
        
        public int getValue(){
            return counter.get();
        }
    }
    
    private static PinState lastState = PinState.HIGH;
    private static PinState currentState = PinState.HIGH;
    
    protected static class RotaryListener implements GpioPinListenerDigital {
        
        private final ConsoleCounter consoleCounter;
        
        public RotaryListener(ConsoleCounter consoleCounter){
            this.consoleCounter = consoleCounter;
        }
        
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpdsce) {
            
//            logger.debug(" ----------------- "+ gpdsce.toString()+" -----------------");
//            logger.debug("Pin 0 "+roAPin.getState()+" Pin 1 "+roBPin.getState());
//            logger.debug("Event pin "+gpdsce.getState());
//            logger.debug("----------------------------------------------------------");
            
            
            if(roAPin.isHigh()){                
                if(lastState==PinState.LOW && currentState==PinState.HIGH){
                    consoleCounter.increment();
                }
                if(lastState==PinState.HIGH && currentState==PinState.LOW){
                    consoleCounter.decrement();
                }
                
                lastState = roBPin.getState();
            }else{
                currentState = roBPin.getState();
            }
            
//            if(gpdsce.getState().isHigh()){
//                consoleCounter.increment();
//                logger.debug("Rotates clockwise, increment "+consoleCounter.getValue());
//            }else{
//                consoleCounter.decrement();
//                logger.debug("Rotates anticlockwise, increment "+consoleCounter.getValue());
//            }
        }        
    }
        
}
