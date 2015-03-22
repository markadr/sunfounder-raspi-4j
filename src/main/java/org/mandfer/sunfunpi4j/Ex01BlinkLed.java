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
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import java.io.Console;
import java.util.Scanner;

/**
 * Blink led on GPIO 0
 *
 */
public class Ex01BlinkLed extends BaseSketch {    
    
    private final Pin pinNumber = RaspiPin.GPIO_00;
    private GpioPinDigitalOutput led;
    
    public Ex01BlinkLed(GpioController gpio){
        super(gpio);
    }
    
    public static void main(String[] args) throws InterruptedException {
        Ex01BlinkLed _01led = new Ex01BlinkLed( GpioFactory.getInstance());
        _01led.run(); // Use Ctrl+C to stop this program.
    }    
    
    @Override
    protected void setup() {
        led = gpio.provisionDigitalOutputPin(pinNumber);
        System.out.println("linker LedPin : "+pinNumber+"(wiringPi pin)");
    }

    @Override
    protected void loop() throws InterruptedException{
        do{
            led.low();
            Thread.sleep(500);
            led.high();
            Thread.sleep(500);            
        }while(isNotInterrupted);
    }

}