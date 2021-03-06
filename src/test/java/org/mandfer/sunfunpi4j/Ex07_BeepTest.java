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

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mandfer.categories.FastTest;
import static org.mockito.Matchers.any;
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
public class Ex07_BeepTest extends BaseSketchTest {
   
    private Ex07_Beep sketch;    
    private GpioPinDigitalOutput mocked_buzzer;
    
    @Before
    public void setUp(){        
        sketch = new Ex07_Beep(mocked_gpioController);        
        mocked_buzzer = mock(GpioPinDigitalOutput.class);        
        when(mocked_gpioController.provisionDigitalOutputPin(any(Pin.class)))
          .thenReturn(mocked_buzzer);        
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                logger.debug("Buzzer is on.");
                return null;
            }
        }).when(mocked_buzzer).low();
        
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                logger.debug("Buzzer is off.");
                return null;
            }
        }).when(mocked_buzzer).high();           
    }
    
    @Test
    @Category(FastTest.class)
    public void testTurnOnAndOffTheBuzzer() throws InterruptedException{
        sketch.setup();
        sketch.setSketchInterruption();
        sketch.loop();
        
        verify(mocked_buzzer).low();
        verify(mocked_buzzer).high();
    }
    
    
}
