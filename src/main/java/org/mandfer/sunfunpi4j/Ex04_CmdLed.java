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
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import java.util.List;

/**
 *
 * @author marcandreuf
 */
public class Ex04_CmdLed extends BaseSketch {
    public static final int NUMOFLEDS = 8;

    private List<GpioPinDigitalOutput> leds;
    
    public Ex04_CmdLed(GpioController gpio) {
        super(gpio);
    }
        
    public static void main(String[] args) throws InterruptedException {
        Ex04_CmdLed ex04CmdLed = new Ex04_CmdLed( GpioFactory.getInstance());
        ex04CmdLed.run(args);
    }

    @Override
    protected void setup() {
        try {
            leds = createListOfPinOutputs(NUMOFLEDS);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    protected void loop(String[] args) throws InterruptedException {        
        int pinNum;
        int pinStatus;
        if (args.length > 0) {
            try {
                pinNum = Integer.parseInt(args[0]);
                pinStatus = Integer.parseInt(args[1]);
                leds.get(pinNum).setState(PinState.getState(pinStatus));
                logger.debug("Press enter to stop.");
                do{}while(isNotInterrupted);
            } catch (NumberFormatException e) {
                logger.error("Arguments 1 is the led number, "
                  + "agrument is 0 for low and 1 for high.");
            }
        }else{
            logger.debug("There are no arguments! Remember to add double"
              + " quotes around the class name and arguments.");
        }        
    }    
}
