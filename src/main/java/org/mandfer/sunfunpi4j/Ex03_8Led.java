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
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.mockito.Mockito.verify;

/**
 *
 * @author marcandreuf
 */
public class Ex03_8Led extends BaseSketch {
    public static final int NUMOFLEDS = 8;

    private List<GpioPinDigitalOutput> leds;
    
    public Ex03_8Led(GpioController gpio) {
        super(gpio);
    }
    
    public static void main(String[] args) throws InterruptedException {
        Ex03_8Led ex38leds = new Ex03_8Led( GpioFactory.getInstance());
        ex38leds.run();
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
    protected void loop() throws InterruptedException {        
        do{
            ledOnFromLeftToRight();
            delay(500);
            ledOffFromRightToLeft();
        }while(isNotInterrupted);
    }

    private void ledOffFromRightToLeft() {
        for(int i=NUMOFLEDS-1;i>=0;i--){
            leds.get(i).low();
            delay(100);
            leds.get(i).high();
        }
    }

    private void ledOnFromLeftToRight() {
        for(GpioPinDigitalOutput led : leds){
            led.low();
            delay(100);
            led.high();
        }
    }

    public List<GpioPinDigitalOutput> createListOfPinOutputs(int numOfPins) throws Exception {
        Pin pin;
        List<GpioPinDigitalOutput> list = new ArrayList<>();
        if(numOfPins<1) throw new NumberFormatException("The num of leds can not be negative.");
        if(numOfPins>20) throw new NumberFormatException("The maximum number of GPIOs is 20.");
        for (int i = 0; i < numOfPins; i++) {
            pin = getPinByNumber(i);
            list.add(gpio.provisionDigitalOutputPin(pin));
            logger.debug("linker LedPin : GPIO "+pin.getAddress()+"(wiringPi pin)");            
        }
        return list;
    }
    
    public Pin getPinByNumber(int gpioNumber) throws Exception{
        try {
            String formattedPinNumber = String.format("%02d", gpioNumber);
            return (Pin) RaspiPin.class.getDeclaredField("GPIO_" + formattedPinNumber).get(Pin.class);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            logger.error(ex.getMessage(), ex);
            throw new Exception(ex.getMessage(), ex);
        }        
    }

    
}
