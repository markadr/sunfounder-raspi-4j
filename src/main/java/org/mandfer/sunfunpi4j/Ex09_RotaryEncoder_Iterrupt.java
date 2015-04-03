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
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioInterrupt;
import com.pi4j.wiringpi.GpioInterruptEvent;
import com.pi4j.wiringpi.GpioInterruptListener;
import com.pi4j.wiringpi.GpioUtil;
import static org.mandfer.sunfunpi4j.BaseSketch.logger;


/**
 * This class reads the events coming from the rotary encoder. 
 * 
 * Looks like the state changes of the encoder are too fast for the interrupt listener to read all the 
 * sequence of changes. Some of the events are missed and there is no way to read proper values of
 * pin A and B with the encoder device from Keyes. With a better encoder and extra capacitors to 
 * control de denounce issue, should work fine with this code to read the state changes as expected
 * for a quadrature wave. 
 *
 * @author marcandreuf
 */
public class Ex09_RotaryEncoder_Iterrupt extends BaseSketch {   
    
    public Ex09_RotaryEncoder_Iterrupt(GpioController gpio) {
        super(gpio);
    }
        
    public static void main(String[] args) throws InterruptedException {
        Ex09_RotaryEncoder_Iterrupt ex09_rotaryEncoder = new Ex09_RotaryEncoder_Iterrupt(GpioFactory.getInstance());
        ex09_rotaryEncoder.run(args);
    }

    @Override
    protected void setup() {
        
        if (Gpio.wiringPiSetup() == -1) {
            logger.debug(" ==>> GPIO SETUP FAILED");
            return;
        }
        
        GpioInterrupt.addListener(new GpioInterruptListener() {
            @Override
            public void pinStateChange(GpioInterruptEvent event) {
                logger.debug("Raspberry Pi PIN [" + event.getPin() +
                  "] is in STATE [" + event.getState() + "]");
            }
        });
        
        GpioUtil.export(0, GpioUtil.DIRECTION_IN);
        GpioUtil.setEdgeDetection(0, GpioUtil.DIRECTION_HIGH);
        Gpio.pinMode(0, Gpio.INPUT);
        Gpio.pullUpDnControl(0, Gpio.PUD_UP);        
        GpioInterrupt.enablePinStateChangeCallback(0);
        
        logger.debug("Rotary encoder ready.");
    }   
    
    @Override
    protected void loop(String[] args) throws InterruptedException {        
        countDownLatchEndSketch.await();
    }
        
}
