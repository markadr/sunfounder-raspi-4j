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

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mandfer.categories.SlowTest;
import static org.mandfer.sunfunpi4j.Ex06_Rgb.LEDPINBLUE;
import static org.mandfer.sunfunpi4j.Ex06_Rgb.LEDPINGREEN;
import static org.mandfer.sunfunpi4j.Ex06_Rgb.LEDPINRED;
import static org.mockito.Matchers.anyInt;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author marcandreuf
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SoftPwm.class, Gpio.class})
public class Ex06_RgbTest extends BaseSketchTest {
   
    private Ex06_Rgb sketch;    
    
    @Before
    public void setUp(){        
        sketch = new Ex06_Rgb(mocked_gpioController);
    
        PowerMockito.mockStatic(Gpio.class);
        
        PowerMockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {                
                logger.debug("WiringPiSetup called.");
                return null;
            }
        }).when(Gpio.class);
        Gpio.wiringPiSetup();

        
        PowerMockito.mockStatic(SoftPwm.class); 
        
        PowerMockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {                
                Object[] args = invocation.getArguments();
                int ledColor = (Integer) args[0];
                int initValue = (Integer) args[1];
                int endValue = (Integer) args[2];
                logger.debug("Led "+ledColor
                  +" set init value to "+initValue
                  +" set end value to "+endValue);
                return null;
            }
        }).when(SoftPwm.class);
        SoftPwm.softPwmCreate(anyInt(), anyInt(), anyInt());
        
        PowerMockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {                
                Object[] args = invocation.getArguments();
                int ledColor = (Integer) args[0];
                int value = (Integer) args[1];
                logger.debug("Led "+ledColor+" set value to "+value);
                return null;
            }
        }).when(SoftPwm.class);
        SoftPwm.softPwmWrite(anyInt(), anyInt());
           
    }
    
    @Test
    @Category(SlowTest.class)
    public void testInitValuesOfRgbColorToASoftPwmPin() throws InterruptedException{
        sketch.setup();
        sketch.setSketchInterruption();
        sketch.loop();
        
        PowerMockito.verifyStatic();
        Gpio.wiringPiSetup();
        
        PowerMockito.verifyStatic();
        SoftPwm.softPwmCreate(LEDPINRED, 0, 100);
        
        PowerMockito.verifyStatic();
        SoftPwm.softPwmCreate(LEDPINGREEN, 0, 100);
        
        PowerMockito.verifyStatic();
        SoftPwm.softPwmCreate(LEDPINBLUE, 0, 100);     
    }
    
    @Test
    @Category(SlowTest.class)
    public void testLedColorSetWithColorType() throws InterruptedException{
        sketch.ledColorSet(Color.RED);
        
        PowerMockito.verifyStatic();
        SoftPwm.softPwmWrite(LEDPINRED, 0xff);
        PowerMockito.verifyStatic();
        SoftPwm.softPwmWrite(LEDPINGREEN, 0x00);
        PowerMockito.verifyStatic();
        SoftPwm.softPwmWrite(LEDPINBLUE, 0x00);
    }
    
    @Test
    @Category(SlowTest.class)
    public void testLedColorSetWithDirectRGBValues() throws InterruptedException{
        sketch.ledColorSet(0x94, 0x00, 0xd3);
        
        PowerMockito.verifyStatic();
        SoftPwm.softPwmWrite(LEDPINRED, 0x94);
        PowerMockito.verifyStatic();
        SoftPwm.softPwmWrite(LEDPINGREEN, 0x00);
        PowerMockito.verifyStatic();
        SoftPwm.softPwmWrite(LEDPINBLUE, 0xd3);
    }
}
