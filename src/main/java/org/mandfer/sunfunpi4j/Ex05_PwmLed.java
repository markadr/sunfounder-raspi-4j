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
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

/**
 *
 * @author marcandreuf
 */
public class Ex05_PwmLed extends BaseSketch {

    private GpioPinPwmOutput dimableLed;
    private int nonPwmPin = 2;
    
    public Ex05_PwmLed(GpioController gpio) {
        super(gpio);
    }
        
    public static void main(String[] args) throws InterruptedException {
        Ex05_PwmLed ex05Pwmled = new Ex05_PwmLed( GpioFactory.getInstance());
        ex05Pwmled.run(args);
    }

    @Override
    protected void setup() {
        //Gpio.wiringPiSetup(); Uncommend for SoftPwm
        //SoftPwm.softPwmCreate(nonPwmPin, 0, 1024);
        dimableLed = gpio.provisionPwmOutputPin(RaspiPin.GPIO_01);
    }

    @Override
    protected void loop(String[] args) throws InterruptedException {        
        do{
            for(int intensity=0;intensity<1024; intensity++){
                //SoftPwm.softPwmWrite(nonPwmPin, intensity);
                dimableLed.setPwm(intensity);
                delay(2);
            }
            delay(1000);
            for(int intensity=1023;intensity>=0;intensity--){
                //SoftPwm.softPwmWrite(nonPwmPin, intensity);
                dimableLed.setPwm(intensity);
                delay(2);
            }            
        }while(isNotInterrupted);
    }    
}
