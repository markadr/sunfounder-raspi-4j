/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mandfer.sunfunpi4j;

import com.pi4j.io.gpio.GpioController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcandreuf
 */
public class SampleSketch extends BaseSketch {
    
    
    public static void main(String[] args) throws InterruptedException {
        SampleSketch sampleSketch = new SampleSketch(null);
        sampleSketch.run();
    }

    public SampleSketch(GpioController gpio) {
        super(gpio);
    }
    
    
    public void loop(){
        int i =0;
        while(isNotInterrupted){
            System.out.println("Num: "+i++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SampleSketch.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    @Override
    protected void setup() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
