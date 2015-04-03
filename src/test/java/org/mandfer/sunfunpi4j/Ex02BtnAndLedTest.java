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
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mandfer.categories.FastTest;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class Ex02BtnAndLedTest extends BaseSketchTest {
    private GpioPinDigitalOutput mocked_led;
    private GpioPinDigitalInput mocked_button;
    private Ex02_BtnAndLed sketch;

    @Before
    public void setUp(){
        mocked_led = mock(GpioPinDigitalOutput.class);
        mocked_button = mock(GpioPinDigitalInput.class);

        sketch = new Ex02_BtnAndLed(mocked_gpioController);
        
        when(mocked_gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00))
                .thenReturn(mocked_led);
        when(mocked_gpioController.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_UP))
                .thenReturn(mocked_button);
        mockLedBehaviour(0, mocked_led);
    }

    @Test
    @Category(FastTest.class)
    public void testLedKeepsHighIfButtonIsNotPressed() throws InterruptedException{
        when(mocked_button.isLow()).thenReturn(false);
        
        sketch.setup();
        sketch.setSketchInterruption();
        sketch.loop();

        verify(mocked_led).high();
        verify(mocked_led, Mockito.never()).low();
    }

    @Test
    @Category(FastTest.class)
    public void testLedGetsLowIfButtonIsPressed() throws InterruptedException{
        when(mocked_button.isLow()).thenReturn(true);

        sketch.setup();
        sketch.setSketchInterruption();
        sketch.loop();

        verify(mocked_led).high();
        verify(mocked_led).low();
    }
}
