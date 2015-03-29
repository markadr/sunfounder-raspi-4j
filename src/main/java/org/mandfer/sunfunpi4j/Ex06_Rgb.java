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
import com.pi4j.wiringpi.SoftPwm;
import java.awt.Color;


/**
 *
 * @author marcandreuf
 */
public class Ex06_Rgb extends BaseSketch {
    
    public static final int LEDPINRED = 0;
    public static final int LEDPINGREEN = 1;
    public static final int LEDPINBLUE = 2;
    
    public Ex06_Rgb(GpioController gpio) {
        super(gpio);
    }
        
    public static void main(String[] args) throws InterruptedException {
        Ex06_Rgb ex06_Rgb = new Ex06_Rgb( GpioFactory.getInstance());
        ex06_Rgb.run(args);
    }

    @Override
    protected void setup() {
        Gpio.wiringPiSetup();
        ledInit();
    }

    private void ledInit() {
        SoftPwm.softPwmCreate(LEDPINRED, 0, 100);
        SoftPwm.softPwmCreate(LEDPINGREEN, 0, 100);
        SoftPwm.softPwmCreate(LEDPINBLUE, 0, 100);
    }    
    
    @Override
    protected void loop(String[] args) throws InterruptedException {        
        do{
             ledColorSet(Color.RED);
             delay(500);
             ledColorSet(Color.GREEN);
             delay(500);
             ledColorSet(Color.BLUE);
             delay(500);
             ledColorSet(Color.YELLOW);
             delay(500);
             ledColorSet(Color.PINK);
             delay(500);
             
             ledColorSet(0x94, 0x00, 0xd3);
             delay(500);
             ledColorSet(0x76, 0xee, 0x00);
             delay(500);
             ledColorSet(0x00, 0xc5, 0xcd);
             delay(500);         
        }while(isNotInterrupted);
    }    

    protected void ledColorSet(Color color) {
        ledColorSet(color.getRed(), color.getGreen(), color.getBlue());
    }

    void ledColorSet(int redValue, int greenValue, int blueValue) {
        SoftPwm.softPwmWrite(LEDPINRED, redValue);
        SoftPwm.softPwmWrite(LEDPINGREEN, greenValue);
        SoftPwm.softPwmWrite(LEDPINBLUE, blueValue);
    }

}
