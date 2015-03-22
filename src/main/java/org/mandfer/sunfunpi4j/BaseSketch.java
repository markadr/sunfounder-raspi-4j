/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mandfer.sunfunpi4j;

import com.pi4j.io.gpio.GpioController;
import java.util.Scanner;

/**
 *
 * @author marcandreuf
 */
public abstract class BaseSketch {

    protected final GpioController gpio;
    protected static Thread threadCheckInputStream;
    //protected Logger logger = LoggerFactory.getLogger(BaseSketch.class.getName());
    protected boolean isNotInterrupted = true;

    protected abstract void setup();

    protected abstract void loop() throws InterruptedException;

    public BaseSketch(GpioController gpio) {
        this.gpio = gpio;
    }

    protected void run() throws InterruptedException {
        setup();
        startThreadToCheckInputStream();
        loop();
        tearDown();
    }

    private void startThreadToCheckInputStream() {
        CheckEnd checkend = new CheckEnd();
        threadCheckInputStream = new Thread(checkend);
        threadCheckInputStream.start();
    }

    protected void tearDown() {
        //logger.debug
        System.out.println("Shutting down gpio.");
        gpio.shutdown();
    }

    protected void delay(long miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException ex) {
            //logger.error
            System.out.println("Sleep delay interrupted " + ex.getMessage());
        }
    }

    protected void interruptSketch() {
        if (threadCheckInputStream != null && threadCheckInputStream.isAlive()) {
            threadCheckInputStream.interrupt();
        }
        isNotInterrupted = false;
    }

    private class CheckEnd implements Runnable {

        Scanner scanner = new Scanner(System.in);

        public void run() {
            while (!scanner.hasNextLine()) {
            };
            //logger.debug
            System.out.println("Scketch interrupted.");
            scanner.close();
            isNotInterrupted = false;
        }
    }

}
