package org.mandfer.sunfunpi4j;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import org.junit.Test;
import static org.mockito.Matchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


public class Ex01BlinkLedTest {    

    /**
     * Blink the led without a physical led. 
     * 
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testBlinkingLed() throws InterruptedException{
        int testLoops = 3;
        GpioController mocked_gpioController = mock(GpioController.class);
        GpioPinDigitalOutput mocked_led = mock(GpioPinDigitalOutput.class);
        
        when(mocked_gpioController.provisionDigitalOutputPin(any(Pin.class)))
          .thenReturn(mocked_led);
                
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
        
        Ex01BlinkLed _01led = new Ex01BlinkLed(mocked_gpioController);
        _01led.setup();
        _01led.interruptSketch();
        _01led.loop();
        
        verify(mocked_gpioController).provisionDigitalOutputPin(any(Pin.class));
        verify(mocked_led, Mockito.atLeast(1) ).low();
        verify(mocked_led, Mockito.atLeast(1) ).high();        
         
    }
}
