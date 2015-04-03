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
import com.pi4j.io.gpio.PinState;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mandfer.categories.SlowTest;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;

/**
 *
 * @author marcandreuf
 */
public class Ex04_CmdLedTest extends BaseSketchTest {
    private List<GpioPinDigitalOutput> mocked_gpioPinDigOutputs;
    private Ex04_CmdLed sketch;
    
    
    @Before
    public void setUp(){        
        sketch = new Ex04_CmdLed(mocked_gpioController);
        mocked_gpioPinDigOutputs = setUpLedStrip();             
    }
    
    @Test
    @Category(SlowTest.class)
    public void testPinAtGivenNumberIsAtGivenState() throws InterruptedException{
        String[] sample_arguments = {"2","0"};
        
        sketch.setup();
        sketch.setSketchInterruption();
        sketch.loop(sample_arguments);
        
        verify(mocked_gpioPinDigOutputs.get(0), Mockito.never()).low();
        verify(mocked_gpioPinDigOutputs.get(1), Mockito.never()).low();
        verify(mocked_gpioPinDigOutputs.get(2)).setState(PinState.getState(0));        
    }
}
