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
import static org.mandfer.sunfunpi4j.Ex08_Motor.*;
import org.mandfer.sunfunpi4j.Ex09_RotaryEncoder.RotaryListener;
import org.mockito.InOrder;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 *
 * @author marcandreuf
 */
public class Ex09_RotaryEncoderTest extends BaseSketchTest {
   
    private Ex09_RotaryEncoder sketch;
    private GpioPinDigitalInput mocked_roAPin;
    private GpioPinDigitalInput mocked_roBPin;
    private Ex09_RotaryEncoder.ConsoleCounter mocked_counter;
    
    @Before
    public void setUp(){        
        mocked_roAPin = mock(GpioPinDigitalInput.class);
        mocked_roBPin = mock(GpioPinDigitalInput.class);
        mocked_counter = mock(Ex09_RotaryEncoder.ConsoleCounter.class);
        sketch = new Ex09_RotaryEncoder(mocked_gpioController);        
    }
    
    @Test
    @Ignore
    public void testSetupMotorPins() throws InterruptedException{
        when(mocked_gpioController.provisionDigitalInputPin(RaspiPin.GPIO_01)).thenReturn(mocked_roBPin);
        
        sketch.setup();
        
        verify(mocked_gpioController).provisionDigitalInputPin(RaspiPin.GPIO_00);
        verify(mocked_gpioController).provisionDigitalInputPin(RaspiPin.GPIO_01);
        verify(mocked_roBPin).addListener(any(RotaryListener.class));
    }
    
    @Test
    @Ignore
    public void testIncrementCounterIfTurnRotaryClockwise() throws InterruptedException{
        GpioPinDigitalStateChangeEvent mocked_gpdsce = mock(GpioPinDigitalStateChangeEvent.class);
        RotaryListener rotaryListener = new RotaryListener(mocked_counter);
        
        when(mocked_gpdsce.getState()).thenReturn(PinState.HIGH);
        
        rotaryListener.handleGpioPinDigitalStateChangeEvent(mocked_gpdsce);
        
        verify(mocked_counter).increment();
    }
    
    @Test
    @Ignore
    public void testDecrementCounterIfTurnRotaryAniclockwise() throws InterruptedException{
        GpioPinDigitalStateChangeEvent mocked_gpdsce = mock(GpioPinDigitalStateChangeEvent.class);
        RotaryListener rotaryListener = new RotaryListener(mocked_counter);
        
        when(mocked_gpdsce.getState()).thenReturn(PinState.LOW);
        
        rotaryListener.handleGpioPinDigitalStateChangeEvent(mocked_gpdsce);
        
        verify(mocked_counter).decrement();
    }
    
    @Test
    @Ignore
    public void testIncrementValue(){
        Ex09_RotaryEncoder.ConsoleCounter consoleCounter = new Ex09_RotaryEncoder.ConsoleCounter();
        
        consoleCounter.increment();
        consoleCounter.increment();
        assertTrue(consoleCounter.getValue() == 2);
        
        consoleCounter.decrement();
        assertTrue(consoleCounter.getValue() == 1);
        
    }
    
}
