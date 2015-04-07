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
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/**
 * Super class with sketch abstraction methods.
 * 
 * @author marcandreuf
 */
public abstract class BaseSketch {

    protected static final Logger logger = LoggerFactory.getLogger(BaseSketch.class.getName());
    protected final GpioController gpio;
    protected static Thread threadCheckInputStream;
    protected boolean isNotInterrupted = true;
    protected static final CountDownLatch countDownLatchEndSketch = new CountDownLatch(1);

    protected abstract void setup();
    protected abstract void loop(String[] args) throws InterruptedException;   
    
    protected void loop() throws InterruptedException{
        loop(new String[0]);
    }

    public BaseSketch() {
        this(null);
    }
    
    public BaseSketch(GpioController gpio) {
        this.gpio = gpio;
    }

    protected void run(String[] args) throws InterruptedException {
        setup();
        startThreadToCheckInputStream();
        loop(args);
        tearDown();
    }

    private void startThreadToCheckInputStream() {
        CheckEnd checkend = new CheckEnd();
        threadCheckInputStream = new Thread(checkend);
        threadCheckInputStream.start();
    }

    protected void tearDown() {
        logger.debug("Shutting down gpio.");
        if(gpio != null){
            gpio.shutdown();
        }
    }

    protected void delay(long miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException ex) {
            logger.error("Sleep delay interrupted " + ex.getMessage());
        }
    }

    protected void setSketchInterruption() {
        if (threadCheckInputStream != null && threadCheckInputStream.isAlive()) {
            threadCheckInputStream.interrupt();
        }
        isNotInterrupted = false;
    }

    private class CheckEnd implements Runnable {

        Scanner scanner = new Scanner(System.in);

        @Override
        @SuppressWarnings("empty-statement")
        public void run() {
            while (!scanner.hasNextLine()) {};
            logger.debug("Sketch interrupted.");
            scanner.close();
            isNotInterrupted = false;
            countDownLatchEndSketch.countDown();
        }
    }
    
    protected List<GpioPinDigitalOutput> createListOfPinOutputs(int numOfPins) throws Exception {
        Pin pin;
        List<GpioPinDigitalOutput> list = new ArrayList<>();
        if(numOfPins<1) throw new NumberFormatException("The num of leds can not be negative.");
        if(numOfPins>20) throw new NumberFormatException("The maximum number of GPIOs is 20.");
        for (int i = 0; i < numOfPins; i++) {
            pin = RaspiPin.getPinByName("GPIO "+i);
            list.add(gpio.provisionDigitalOutputPin(pin));
            logger.debug("linker LedPin : GPIO "+pin.getAddress()+"(wiringPi pin)");            
        }
        return list;
    }    
    
    protected static void wiringPiSetup(){
        if (Gpio.wiringPiSetup() == -1) {
            String msg = "==>> GPIO SETUP FAILED";
            logger.debug(msg);
            throw new RuntimeException(msg);
        }
    }

}
