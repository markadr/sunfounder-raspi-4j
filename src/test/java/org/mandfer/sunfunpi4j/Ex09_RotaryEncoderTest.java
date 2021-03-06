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
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mandfer.categories.SlowTest;
import static org.mockito.Mockito.mock;

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
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(3, 0) == -3); //0
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(0, 5) == 1); //6
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(5, 6) == 1); //12
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(6, 3) == -3); //6
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(0, 6) == 2); //8
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(5, 3) == -2); //6
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(6, 0) == -2); //4
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(3, 5) == 2); //10

        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(3, 6) == 3); //12
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(6, 5) == -1); //10
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(5, 0) == -1);
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(0, 3) == 3);
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(6, 0) == -2);
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(3, 5) == 2);
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(0, 6) == 2);
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(5, 3) == -2);
    }

    @Test
    @Ignore
    public void rotaryEncoderStateTest() {
        assertTrue(Ex09_RotaryEncoder_SMPC.rotaryEncoderState(0, 0) == 0);
        assertTrue(Ex09_RotaryEncoder_SMPC.rotaryEncoderState(1, 0) == 5);
        assertTrue(Ex09_RotaryEncoder_SMPC.rotaryEncoderState(1, 1) == 6);
        assertTrue(Ex09_RotaryEncoder_SMPC.rotaryEncoderState(0, 1) == 3);
    }

    @Test
    @Ignore
    public void rotarySeqTest() {
        System.out.println("0,0: " + Ex09_RotaryEncoder_SMPC.calcSeq(0, 0));
        System.out.println("1,0: " + Ex09_RotaryEncoder_SMPC.calcSeq(1, 0));
        System.out.println("1,1: " + Ex09_RotaryEncoder_SMPC.calcSeq(1, 1));
        System.out.println("0,1: " + Ex09_RotaryEncoder_SMPC.calcSeq(0, 1));
    }

    @Test
    @Ignore
    public void testEncodedValues() {
        System.out.println("0,0: " + Ex09_RotaryEncoder_SMPC.calcEncoded(0, 0));
        System.out.println("1,0: " + Ex09_RotaryEncoder_SMPC.calcEncoded(1, 0));
        System.out.println("1,1: " + Ex09_RotaryEncoder_SMPC.calcEncoded(1, 1));
        System.out.println("0,1: " + Ex09_RotaryEncoder_SMPC.calcEncoded(0, 1));
    }    
    
    @Test
    @Ignore
    public void testPinStateValues() {
        System.out.println("0,1: " + Ex09_RotaryEncoder_SMPC.calcPinState(0, 1));
        System.out.println("0,0: " + Ex09_RotaryEncoder_SMPC.calcPinState(0, 0));
        System.out.println("1,0: " + Ex09_RotaryEncoder_SMPC.calcPinState(1, 0));
        System.out.println("1,1: " + Ex09_RotaryEncoder_SMPC.calcPinState(1, 1));
    }
    
    @Test
    @Ignore
    public void testPinStateRevValues() {
        System.out.println("0,1: " + Ex09_RotaryEncoder_SMPC.calcPinStateRev(0, 1));
        System.out.println("0,0: " + Ex09_RotaryEncoder_SMPC.calcPinStateRev(0, 0));
        System.out.println("1,0: " + Ex09_RotaryEncoder_SMPC.calcPinStateRev(1, 0));
        System.out.println("1,1: " + Ex09_RotaryEncoder_SMPC.calcPinStateRev(1, 1));
    }
}
