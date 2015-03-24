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

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class holds all the common code in the jUnit sketch tests.
 * 
 * @author marcandreuf
 */
public abstract class BaseSketchTest {    
    
    protected final Logger logger = LoggerFactory.getLogger(BaseSketch.class.getName());
    protected final GpioController mocked_gpioController;
    
    public BaseSketchTest(){
        mocked_gpioController = mock(GpioController.class);
    }
    
    /**
     * This method prepared the mocked led to print logging information.
     * 
     * @param gpioPinNumber
     * @param mocked_led 
     */
    protected void mockLedBehaviour(final int gpioPinNumber, GpioPinDigitalOutput mocked_led){
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                logger.debug("Gpio "+gpioPinNumber+" is low.");
                return null;
            }
        }).when(mocked_led).low();
        
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                logger.debug("Gpio "+gpioPinNumber+" is high.");
                return null;
            }
        }).when(mocked_led).high();
    }
}
