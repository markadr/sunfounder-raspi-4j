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
import com.pi4j.io.gpio.RaspiPin;
import static org.mandfer.sunfunpi4j.BaseSketch.logger;
import org.markadr.RotaryEncoder;
import org.markadr.RotaryEncoderListener;

/**
 * This is an example about how to use event listener instead of using polling solution.
 * 
 * This example uses the RotaryEncoder from Mark R. https://gist.github.com/markadr
 * 
 * With the rotary encoder from Keyes does not work very well. I assume the rotary encoder
 * used by Mark has different specifications.
 *
 * @author marcandreuf
 */
public class Ex09_RotaryEncoder_RoEncListener extends BaseSketch {   

    private static GpioPinDigitalInput roAPin;
    private static GpioPinDigitalInput roBPin;
    
    public Ex09_RotaryEncoder_RoEncListener(GpioController gpio) {
        super(gpio);
    }
        
    public static void main(String[] args) throws InterruptedException {
        Ex09_RotaryEncoder_RoEncListener ex09_rotaryEncoder = new Ex09_RotaryEncoder_RoEncListener(GpioFactory.getInstance());
        ex09_rotaryEncoder.run(args);
    }

    @Override
    protected void setup() {
        RotaryEncoder rotaryEncoder = new RotaryEncoder(RaspiPin.GPIO_00, RaspiPin.GPIO_01, 0);
        
        rotaryEncoder.setListener(new RotaryEncoderListener() {
            @Override
            public void up(long encoderValue) {
                logger.debug("Up : "+encoderValue);
            }
            @Override
            public void down(long encoderValue) {
                logger.debug("Down: "+encoderValue);
            }
        });
        
        logger.debug("Rotary encoder ready.");
    }
    
    @Override
    protected void loop(String[] args) throws InterruptedException {        
        countDownLatchEndSketch.await();
    }
}
