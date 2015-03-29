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
import com.pi4j.io.gpio.RaspiPin;
import java.util.concurrent.TimeUnit;


/**
 * This class has an extra TimeUnit dependency which allows us to test the duration 
 * when the motor is on.
 *
 * @author marcandreuf
 */
public class Ex08_Motor extends BaseSketch {
    
    public static final int DURATION_TURNING_CLOCKWISE = 3;
    public static final int DURATION_STOPED = 2;
    public static final int DURATION_TURNING_ANITCLOCKWISE = 6;
    
    private final TimeUnit timer;   
    private GpioPinDigitalOutput motorPin1;
    private GpioPinDigitalOutput motorPin2;
    private GpioPinDigitalOutput motorEnable;
    
    
    public Ex08_Motor(GpioController gpio, TimeUnit timer) {
        super(gpio);
        this.timer = timer;
    }
        
    public static void main(String[] args) throws InterruptedException {
        Ex08_Motor ex08_motor = new Ex08_Motor(GpioFactory.getInstance(), TimeUnit.SECONDS);
        ex08_motor.run(args);
    }

    @Override
    protected void setup() {
        motorPin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00);
        motorPin2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
        motorEnable = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02);
    }
    
    @Override
    protected void loop(String[] args) throws InterruptedException {        
        do{
            turnClockWise(DURATION_TURNING_CLOCKWISE);
            stopMotor(DURATION_STOPED);
            turnAntiClockWise(DURATION_TURNING_ANITCLOCKWISE);
            stopMotor(DURATION_STOPED);
        }while(isNotInterrupted);
        
        motorEnable.low();
    }
    
    private void turnClockWise(int timeUnits) throws InterruptedException{
        logger.debug("Turn clockwise for "+timeUnits+" seconds.");
        motorEnable.high();
        motorPin1.high();
        motorPin2.low();
        timer.sleep(timeUnits);
    }
    
    private void stopMotor(int timeUnits) throws InterruptedException{
        logger.debug("Stop motor for "+timeUnits+" seconds.");
        motorEnable.low();            
        timer.sleep(timeUnits);
    }
    
    private void turnAntiClockWise(int timeUnits) throws InterruptedException{
        logger.debug("Turn anticlockwise for "+timeUnits+" seconds.");
        motorEnable.high();
        motorPin1.low();
        motorPin2.high();            
        timer.sleep(timeUnits);
    }
        
}
