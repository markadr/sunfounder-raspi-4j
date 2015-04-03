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
import com.pi4j.io.gpio.RaspiPin;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mandfer.categories.FastTest;
import org.mandfer.categories.SlowTest;
import static org.mandfer.sunfunpi4j.Ex08_Motor.DURATION_STOPED;
import static org.mandfer.sunfunpi4j.Ex08_Motor.DURATION_TURNING_ANITCLOCKWISE;
import static org.mandfer.sunfunpi4j.Ex08_Motor.DURATION_TURNING_CLOCKWISE;
import org.mockito.InOrder;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 *
 * @author marcandreuf
 */
public class Ex08_MotorTest extends BaseSketchTest {
   
    private Ex08_Motor sketch;
    private TimeUnit mocked_timer;
    private GpioPinDigitalOutput mocked_motorPin1;
    private GpioPinDigitalOutput mocked_motorPin2;
    private GpioPinDigitalOutput mocked_motorEnable;
    
    @Before
    public void setUp(){        
        mocked_timer = mock(TimeUnit.class);
        mocked_motorPin1 = mock(GpioPinDigitalOutput.class);
        mocked_motorPin2 = mock(GpioPinDigitalOutput.class);
        mocked_motorEnable = mock(GpioPinDigitalOutput.class);
        sketch = new Ex08_Motor(mocked_gpioController, mocked_timer);        
    }
    
    @Test
    @Category(FastTest.class)
    public void testSetupMotorPins() throws InterruptedException{
        sketch.setup();
        
        verify(mocked_gpioController).provisionDigitalOutputPin(RaspiPin.GPIO_00);
        verify(mocked_gpioController).provisionDigitalOutputPin(RaspiPin.GPIO_01);
        verify(mocked_gpioController).provisionDigitalOutputPin(RaspiPin.GPIO_02);
        verifyZeroInteractions(mocked_timer);
    }
    
    @Test
    @Category(SlowTest.class)
    public void testTurnMotorClockwiseStop3SecondsTurnMotorAnticlockwise() throws InterruptedException{
        
        when(mocked_gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00)).thenReturn(mocked_motorPin1);
        when(mocked_gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_01)).thenReturn(mocked_motorPin2);
        when(mocked_gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_02)).thenReturn(mocked_motorEnable);
        
        sketch.setup();
        sketch.setSketchInterruption();
        sketch.loop();
        
        InOrder inOrder = Mockito.inOrder(
                    mocked_motorEnable, mocked_motorPin1,
                    mocked_motorPin2, mocked_timer);
        
        inOrder.verify(mocked_motorEnable).high();
        inOrder.verify(mocked_motorPin1).high();
        inOrder.verify(mocked_motorPin2).low();
        inOrder.verify(mocked_timer).sleep(DURATION_TURNING_CLOCKWISE);
        
        inOrder.verify(mocked_motorEnable).low();
        inOrder.verify(mocked_timer).sleep(DURATION_STOPED);
        
        inOrder.verify(mocked_motorEnable).high();
        inOrder.verify(mocked_motorPin1).low();
        inOrder.verify(mocked_motorPin2).high();
        inOrder.verify(mocked_timer).sleep(DURATION_TURNING_ANITCLOCKWISE);
        
        inOrder.verify(mocked_motorEnable).low();
        inOrder.verify(mocked_timer).sleep(DURATION_STOPED);  
        
        inOrder.verify(mocked_motorEnable).low();
    }
    
}
