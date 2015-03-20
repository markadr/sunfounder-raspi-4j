/**
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Marc Andreu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.mandfer.sunfunpi4j;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioPinShutdown;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import java.util.Map;
import java.util.concurrent.Future;

/**
 *
 * @author marcandreuf
 */
public class Stubed_GpioPinDigitalOutput implements GpioPinDigitalOutput {

    public void high() {
        System.out.println("Gpio is high");
    }

    public void low() {
        System.out.println("Gpio is low");
    }

    public void toggle() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Future<?> blink(long arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Future<?> blink(long arg0, PinState arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Future<?> blink(long arg0, long arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Future<?> blink(long arg0, long arg1, PinState arg2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Future<?> pulse(long arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Future<?> pulse(long arg0, boolean arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Future<?> pulse(long arg0, PinState arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Future<?> pulse(long arg0, PinState arg1, boolean arg2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setState(PinState arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setState(boolean arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isHigh() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isLow() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PinState getState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isState(PinState arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public GpioProvider getProvider() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Pin getPin() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setName(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setTag(Object arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getTag() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setProperty(String arg0, String arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasProperty(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getProperty(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getProperty(String arg0, String arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Map<String, String> getProperties() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeProperty(String arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clearProperties() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void export(PinMode arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void unexport() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isExported() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setMode(PinMode arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PinMode getMode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isMode(PinMode arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPullResistance(PinPullResistance arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PinPullResistance getPullResistance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isPullResistance(PinPullResistance arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public GpioPinShutdown getShutdownOptions() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setShutdownOptions(GpioPinShutdown arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setShutdownOptions(Boolean arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setShutdownOptions(Boolean arg0, PinState arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setShutdownOptions(Boolean arg0, PinState arg1, PinPullResistance arg2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setShutdownOptions(Boolean arg0, PinState arg1, PinPullResistance arg2, PinMode arg3) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
