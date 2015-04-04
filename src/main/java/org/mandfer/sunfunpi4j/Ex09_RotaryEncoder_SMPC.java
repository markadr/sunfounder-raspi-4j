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
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import static org.mandfer.sunfunpi4j.BaseSketch.logger;

/**
 * This example uses a State Machine solution with a Producer Consumer approach to prevent missing events.
 *
 * The state machine solution is based on the Rotary Ardunio library from Ben Buxton.
 * http://www.buxtronix.net/2011/10/rotary-encoders-done-properly.html
 *
 * The two pins has a listener which acts as a producer of events being stored in a BlockingQueue.
 * This prevents missing some state event changes while moving the encoder very fast.
 *
 * The EventsConsumer thread will be calculating the values while there are Events in the BlockingQueue.
 *
 * @author marcandreuf
 */
public class Ex09_RotaryEncoder_SMPC extends BaseSketch {

    private static final int DIR_CW = 0x10;
    private static final int DIR_CCW = 0x20;
    private static final int DIR_FILTER = 0x30;
    private static final int STATE_FILTER = 0xf;
    private static final int R_START = 0x0;
    private static final int R_CW_FINAL = 0x1;
    private static final int R_CW_BEGIN = 0x2;
    private static final int R_CW_NEXT = 0x3;
    private static final int R_CCW_BEGIN = 0x4;
    private static final int R_CCW_FINAL = 0x5;
    private static final int R_CCW_NEXT = 0x6;

    private static final int statesTable[][] = {
        // R_START
        {R_START, R_CW_BEGIN, R_CCW_BEGIN, R_START},
        // R_CW_FINAL
        {R_CW_NEXT, R_START, R_CW_FINAL, R_START | DIR_CW},
        // R_CW_BEGIN
        {R_CW_NEXT, R_CW_BEGIN, R_START, R_START},
        // R_CW_NEXT
        {R_CW_NEXT, R_CW_BEGIN, R_CW_FINAL, R_START},
        // R_CCW_BEGIN
        {R_CCW_NEXT, R_START, R_CCW_BEGIN, R_START},
        // R_CCW_FINAL
        {R_CCW_NEXT, R_CCW_FINAL, R_START, R_START | DIR_CCW},
        // R_CCW_NEXT
        {R_CCW_NEXT, R_CCW_FINAL, R_CCW_BEGIN, R_START},};

    private static GpioPinDigitalInput roAPin;
    private static GpioPinDigitalInput roBPin;

    private volatile int state, counter;
    private BlockingQueue<EventStateChange> eventsQueue;
    private Thread eventsConsumer;

    public Ex09_RotaryEncoder_SMPC(GpioController gpio) {
        super(gpio);
    }

    public static void main(String[] args) throws InterruptedException {
        Ex09_RotaryEncoder_SMPC ex09_rotaryEncoder = new Ex09_RotaryEncoder_SMPC(GpioFactory.getInstance());
        ex09_rotaryEncoder.run(args);
    }

    @Override
    protected void setup() {
        roAPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP);
        roBPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_UP);

        eventsQueue = new ArrayBlockingQueue(1024);
        eventsConsumer = new Thread(new EventsConsumer(eventsQueue));
        eventsConsumer.start();

        roAPin.addListener(new RotaryProducerListener());
        roBPin.addListener(new RotaryProducerListener());
        state = R_START;
        logger.debug("Rotary encoder ready.");
    }

    @Override
    protected void loop(String[] args) throws InterruptedException {
        countDownLatchEndSketch.await();
        if (eventsConsumer.isAlive()) {
            eventsConsumer.interrupt();
        }
    }

    protected class RotaryProducerListener implements GpioPinListenerDigital {
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpdsce) {
            eventsQueue.add(
              new EventStateChange(roAPin.getState().getValue(), roBPin.getState().getValue()));
        }
    }

    private class EventStateChange {
        private final int evPinA;
        private final int evPinB;
        private int pinState, result;

        public EventStateChange(int a, int b) {
            this.evPinA = a;
            this.evPinB = b;
        }

        public void process() {
            pinState = (evPinB << 1) | evPinA;
            state = statesTable[state & STATE_FILTER][pinState];
            result = state & DIR_FILTER;
            // Switched directions because the rotary switch from keyes is pulled up and 
            // provides oposite values.
            if (result == DIR_CW) {
                logger.debug("Value: " + counter--);
            } else if (result == DIR_CCW) {
                logger.debug("Value: " + counter++);
            }
        }
    }

    private class EventsConsumer implements Runnable {
        protected BlockingQueue<EventStateChange> queue = null;

        public EventsConsumer(BlockingQueue<EventStateChange> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    queue.take().process();
                }
            } catch (InterruptedException e) {}
        }
    }

    
    // Sample learning methods ----------------------------
    public static int calcSeq(int a, int b) {
        return (a ^ b) | b << 1;
    }

    public static int rotaryEncoderState(int a, int b) {
        int c = a ^ b;
        return a * 4 + b * 2 + c * 1;
    }

    public static long calcDelta(int lastState, int newState) {
        return (newState - lastState) % 4;
    }

    public static int calcEncoded(int stateA, int stateB) {
        return (stateA << 1) | stateB;
    }

    public static int calcPinState(int stateA, int stateB) {
        return (stateB << 1) | stateA;
    }

    public static int calcPinStateRev(int stateA, int stateB) {
        return (stateA << 1) | stateB;
    }

}
