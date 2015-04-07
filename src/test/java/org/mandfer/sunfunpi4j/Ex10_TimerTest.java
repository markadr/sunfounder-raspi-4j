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
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mandfer.categories.SlowTest;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author marcandreuf
 */
@Category(SlowTest.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest({Gpio.class})
public class Ex10_TimerTest extends BaseSketchTest {

//    private CountPulseCallBack mocked_countPulse = mock(CountPulseCallBack.class);
//    private Ex10_Timer sketch;
//    private CountPulseCallBack countPulseCallBack;

//    @Before
//    public void setUp() {
//        PowerMockito.mockStatic(Gpio.class);
//        sketch = new Ex10_Timer(mocked_countPulse);
//    }

//    @Test
//    @Ignore
//    public void testSetup() {
//        sketch.setup();
//
//        PowerMockito.verifyStatic();
//        Gpio.wiringPiSetup();
//
//        PowerMockito.verifyStatic();
//        Gpio.pinMode(0, Gpio.INPUT);
//
//        PowerMockito.verifyStatic();
//        Gpio.pullUpDnControl(0, Gpio.PUD_DOWN);
//
//        PowerMockito.verifyStatic();
//        Gpio.wiringPiISR(0, Gpio.INT_EDGE_FALLING, mocked_countPulse);
//    }
//
//    @Test
//    @Ignore
//    public void testCountPulse() {
//        GlobalCounter mocked_counter = mock(GlobalCounter.class);
//        countPulseCallBack = new CountPulseCallBack(mocked_counter);
//        countPulseCallBack.callback(0);
//        verify(mocked_counter).countPulse();
//    }
}
