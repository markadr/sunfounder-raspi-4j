/**
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Marc Andreu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.mandfer.sunfunpi4j;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioInterruptCallback;
import static org.mandfer.sunfunpi4j.BaseSketch.logger;

/**
 * @author marcandreuf
 */
public class Ex10_Timer extends BaseSketch {    
     
    public static void main(String[] args) throws InterruptedException {
        Ex10_Timer ex10_timer = new Ex10_Timer();
        Ex10_Timer.gpioIsrStaticSetup();
        ex10_timer.run(args);
    }
    
    private static int counter = 0;
    
    private static void addCount(){
        counter++;
    }
    
    private static class GCounter {
        private int count = 0;
        
        public void addCount(){
            count++;
        }
        
        public int getCount(){
            return count;
        }
    }
    
    private static GCounter gcount = new GCounter();
    
    protected static void gpioIsrStaticSetup(){
        wiringPiSetup();
        Gpio.pinMode(0, Gpio.INPUT);
        Gpio.pullUpDnControl(0, Gpio.PUD_UP);
        Gpio.wiringPiISR(0, Gpio.INT_EDGE_FALLING, new GpioInterruptCallback() {            
                   
            @Override
            public void callback(int pin) {
                if(pin==0){
                    gcount.addCount();
                    logger.debug("Count pulse "+gcount.getCount()+" on pin " + pin);
                }
            }
        });
        logger.debug("ISR static setup done.");        
    }

    @Override
    protected void setup() {
    }

    @Override
    protected void loop(String[] args) throws InterruptedException {
        do {
            countDownLatchEndSketch.await();
        } while (isNotInterrupted);
    }

}

