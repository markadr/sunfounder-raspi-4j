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

import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mandfer.categories.SlowTest;
import org.mockito.Matchers;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author marcandreuf
 */
public class Ex05_PwmLedTest extends BaseSketchTest {
    private GpioPinPwmOutput mocked_dimableLed;
    private Ex05_PwmLed sketch;    
    
    @Before
    public void setUp(){        
        mocked_dimableLed = mock(GpioPinPwmOutput.class);
        sketch = new Ex05_PwmLed(mocked_gpioController);
        
        when(mocked_gpioController.provisionPwmOutputPin(any(Pin.class)))
          .thenReturn(mocked_dimableLed);   
        
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                int intensity = (Integer) args[0];
                logger.debug("Gpio pwm value is "+intensity);
                return null;
            }
        }).when(mocked_dimableLed).setPwm(Mockito.anyInt());
    }
    
    @Test
    @Category(SlowTest.class)
    public void testDimLedInAndout() throws InterruptedException{
        sketch.setup();
        sketch.setSketchInterruption();
        sketch.loop();
        
        verify(mocked_dimableLed, Mockito.times(2)).setPwm(0);
        verify(mocked_dimableLed, Mockito.times(2)).setPwm(500);
        verify(mocked_dimableLed, Mockito.times(2)).setPwm(1000);
        verify(mocked_dimableLed, Mockito.atMost(2048)).setPwm(Matchers.anyInt());        
    }
}
