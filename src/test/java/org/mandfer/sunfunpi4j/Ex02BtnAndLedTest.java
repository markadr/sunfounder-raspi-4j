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
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


public class Ex02BtnAndLedTest {

    private GpioController mocked_gpioController = mock(GpioController.class);
    private GpioPinDigitalOutput mocked_led = mock(GpioPinDigitalOutput.class);
    private GpioPinDigitalInput mocked_button = mock(GpioPinDigitalInput.class);

    @Before
    public void setUp(){
        mocked_gpioController = mock(GpioController.class);
        mocked_led = mock(GpioPinDigitalOutput.class);
        mocked_button = mock(GpioPinDigitalInput.class);

        when(mocked_gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00))
                .thenReturn(mocked_led);
        when(mocked_gpioController.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_UP))
                .thenReturn(mocked_button);

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                System.out.println("Gpio is low.");
                return null;
            }
        }).when(mocked_led).low();

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                System.out.println("Gpio is high.");
                return null;
            }
        }).when(mocked_led).high();
    }

    @Test
    public void testLedKeepsHighIfButtonIsNotPressed(){
        when(mocked_button.isLow()).thenReturn(false);
        
        Ex02BtnAndLed sketch = new Ex02BtnAndLed(mocked_gpioController);
        sketch.setup();
        sketch.setSketchInterruption();
        sketch.loop();

        verify(mocked_led).high();
        verify(mocked_led, Mockito.never()).low();
    }

    @Test
    public void testLedGetsLowIfButtonIsPressed(){
        when(mocked_button.isLow()).thenReturn(true);

        Ex02BtnAndLed sketch = new Ex02BtnAndLed(mocked_gpioController);
        sketch.setup();
        sketch.setSketchInterruption();
        sketch.loop();

        verify(mocked_led).high();
        verify(mocked_led).low();
    }
}
