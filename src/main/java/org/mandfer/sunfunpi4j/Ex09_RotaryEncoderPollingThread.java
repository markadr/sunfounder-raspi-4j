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
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import static org.mandfer.sunfunpi4j.BaseSketch.logger;


/**
 * This class uses a new thread to poll the state change.
 *
 * This solution uses polling which is not very efficient. 
 *
 * @author marcandreuf
 */
public class Ex09_RotaryEncoderPollingThread extends BaseSketch {   

    private static final int MAX_STOP_POLLING_DELAY = 3000;
    
    private GpioPinDigitalInput roAPin;
    private GpioPinDigitalInput roBPin;
    private int globalCounter = 0;
    private Thread pollingPinStateChange;
    private Thread stopPolling;
    private int stopPollingDelay = 2000;
    
    
    public Ex09_RotaryEncoderPollingThread(GpioController gpio) {
        super(gpio);
    }
        
    public static void main(String[] args) throws InterruptedException {
        Ex09_RotaryEncoderPollingThread ex09_rotaryEncoder = 
          new Ex09_RotaryEncoderPollingThread(GpioFactory.getInstance());
        ex09_rotaryEncoder.run(args);
    }

    @Override
    protected void setup() {
        roAPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP);
        roBPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_UP);
        pollingPinStateChange = new Thread(new PollingPinStateChange());
        stopPolling = new Thread(new StopPolling());
        roAPin.addListener(new PinStateChangingListener());
        logger.info("Rotary encoder ready.");
    }
    
    @Override
    protected void loop(String[] args) throws InterruptedException {     
        countDownLatchEndSketch.await();
        
        if(pollingPinStateChange.isAlive()){
            pollingPinStateChange.interrupt();
        }
        if(stopPolling.isAlive()){
            stopPolling.interrupt();
        }
        
    }
    
    private void printCounter(int countValue){
        logger.info("globalCounter : "+countValue);        
    }
    
    private class PinStateChangingListener implements GpioPinListenerDigital {

        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpdsce) {
            if(!pollingPinStateChange.isAlive()){
                pollingPinStateChange = new Thread(new PollingPinStateChange());
                pollingPinStateChange.start();
                stopPolling = new Thread(new StopPolling());
                stopPolling.start();
            }else{
                addStopPollingDelay(100);
            }            
        }        
    }
    
    private void addStopPollingDelay(int moreDelay){
        if(stopPollingDelay > MAX_STOP_POLLING_DELAY){
            stopPollingDelay = stopPollingDelay + moreDelay;
        }
    }    
    
    private class PollingPinStateChange implements Runnable{
       
        @Override
        public void run() {   
            
            boolean flag = false;
            PinState lastRoBStatus;
            PinState currentRoBStatus=PinState.HIGH;
            
            while(!Thread.interrupted()){
                lastRoBStatus = roBPin.getState();

                while(roAPin.isLow()){
                    currentRoBStatus = roBPin.getState();
                    flag=true;
                }

                if(flag){
                    flag=false;
                    if(lastRoBStatus==PinState.LOW && currentRoBStatus==PinState.HIGH){
                        printCounter(globalCounter ++);
                    }
                    if(lastRoBStatus==PinState.HIGH && currentRoBStatus==PinState.LOW){
                        printCounter(globalCounter --);
                    }
                }                
            }
        }        
    }   
    
  
    private class StopPolling implements Runnable{       
        
        @Override
        public void run() {
            while(stopPollingDelay > 0){
                delay(1);
                stopPollingDelay--;
            }
            if(pollingPinStateChange.isAlive()){
                pollingPinStateChange.interrupt();
            }
        }        
    }
}
