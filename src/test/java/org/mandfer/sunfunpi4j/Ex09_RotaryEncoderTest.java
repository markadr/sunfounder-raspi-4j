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

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mandfer.categories.SlowTest;
import static org.mandfer.sunfunpi4j.Ex08_Motor.*;
import org.mandfer.sunfunpi4j.Ex09_RotaryEncoder_SMPC.RotaryProducerListener;
import org.mockito.InOrder;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 *
 * This Exercise 09 has only a set of learning tests. 
 * 
 * To keep things simple enough and not start adding Object Oriented decoupling complexities
 * for now I leave the code without jUnit tests. At least I do not yet know how to unit test
 * without adding a tone of new code which would make things difficult for learning tests.
 * 
 * @author marcandreuf
 */
@Category(SlowTest.class)
public class Ex09_RotaryEncoderTest extends BaseSketchTest {

    private Ex09_RotaryEncoder_SMPC sketch;
    private GpioPinDigitalInput mocked_roAPin;
    private GpioPinDigitalInput mocked_roBPin;

    @Before
    public void setUp() {
        mocked_roAPin = mock(GpioPinDigitalInput.class);
        mocked_roBPin = mock(GpioPinDigitalInput.class);
        sketch = new Ex09_RotaryEncoder_SMPC(mocked_gpioController);
    }

    @Test
    @Ignore
    public void learningTestCalcDelta() {
        assertTrue(sketch.calcDelta(3, 0) == -3); //0
        assertTrue(sketch.calcDelta(0, 5) == 1); //6
        assertTrue(sketch.calcDelta(5, 6) == 1); //12
        assertTrue(sketch.calcDelta(6, 3) == -3); //6
        assertTrue(sketch.calcDelta(0, 6) == 2); //8
        assertTrue(sketch.calcDelta(5, 3) == -2); //6
        assertTrue(sketch.calcDelta(6, 0) == -2); //4
        assertTrue(sketch.calcDelta(3, 5) == 2); //10

        assertTrue(sketch.calcDelta(3, 6) == 3); //12
        assertTrue(sketch.calcDelta(6, 5) == -1); //10
        assertTrue(sketch.calcDelta(5, 0) == -1);
        assertTrue(sketch.calcDelta(0, 3) == 3);
        assertTrue(sketch.calcDelta(6, 0) == -2);
        assertTrue(sketch.calcDelta(3, 5) == 2);
        assertTrue(sketch.calcDelta(0, 6) == 2);
        assertTrue(sketch.calcDelta(5, 3) == -2);
    }

    @Test
    @Ignore
    public void rotaryEncoderStateTest() {
        assertTrue(sketch.rotaryEncoderState(0, 0) == 0);
        assertTrue(sketch.rotaryEncoderState(1, 0) == 5);
        assertTrue(sketch.rotaryEncoderState(1, 1) == 6);
        assertTrue(sketch.rotaryEncoderState(0, 1) == 3);
    }

    @Test
    @Ignore
    public void rotarySeqTest() {
        System.out.println("0,0: " + sketch.calcSeq(0, 0));
        System.out.println("1,0: " + sketch.calcSeq(1, 0));
        System.out.println("1,1: " + sketch.calcSeq(1, 1));
        System.out.println("0,1: " + sketch.calcSeq(0, 1));
    }

    @Test
    @Ignore
    public void testEncodedValues() {
        System.out.println("0,0: " + sketch.calcEncoded(0, 0));
        System.out.println("1,0: " + sketch.calcEncoded(1, 0));
        System.out.println("1,1: " + sketch.calcEncoded(1, 1));
        System.out.println("0,1: " + sketch.calcEncoded(0, 1));
    }
    
    
    @Test
    public void testPinStateValues() {
        System.out.println("0,1: " + sketch.calcPinState(0, 1));
        System.out.println("0,0: " + sketch.calcPinState(0, 0));
        System.out.println("1,0: " + sketch.calcPinState(1, 0));
        System.out.println("1,1: " + sketch.calcPinState(1, 1));
    }
    
    @Test
    public void testPinStateRevValues() {
        System.out.println("0,1: " + sketch.calcPinStateRev(0, 1));
        System.out.println("0,0: " + sketch.calcPinStateRev(0, 0));
        System.out.println("1,0: " + sketch.calcPinStateRev(1, 0));
        System.out.println("1,1: " + sketch.calcPinStateRev(1, 1));
    }
}
