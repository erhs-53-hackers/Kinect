/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinect;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.openkinect.freenect.*;
import sun.awt.image.ByteArrayImageSource;

public class FreenectTest {

    static Context ctx;
    static Device dev;

    public static void initKinect() {

        ctx = Freenect.createContext();


        if (ctx.numDevices() > 0) {
            dev = ctx.openDevice(0);
        } else {
            System.err.println("WARNING: No kinects detected, hardware tests will be implicitly passed.");
        }

        //dev.setVideoFormat(VideoFormat.RGB);
        dev.setVideoFormat(VideoFormat.RGB);

    }

    public static void shutdownKinect() {
        if (ctx != null) {
            if (dev != null) {
                dev.close();
            }
        }
        ctx.shutdown();
    }

    protected void moveAndWait(Device device, int degrees) throws InterruptedException {
        device.refreshTiltState();
        if (device.getTiltAngle() >= (degrees - 2) && device.getTiltAngle() <= (degrees + 2)) {
            return;
        }


        while (device.getTiltStatus() == TiltStatus.STOPPED) {
            device.refreshTiltState();
        }

        if (device.getTiltStatus() == TiltStatus.MOVING) {
            while (device.getTiltStatus() == TiltStatus.MOVING) {
                device.refreshTiltState();
            }
        }

        if (device.getTiltStatus() == TiltStatus.STOPPED) {
            while (device.getTiltAngle() < -32) {
                device.refreshTiltState();
            }
        }
    }

    public void testSetTiltAngle() throws InterruptedException {



        dev.refreshTiltState();

        moveAndWait(dev, 0);


        moveAndWait(dev, 20);


        moveAndWait(dev, -20);


        moveAndWait(dev, 0);

    }

    public void testLogEvents() throws InterruptedException {



        dev.startVideo(new VideoHandler() {

            @Override
            public void onFrameReceived(FrameMode mode, ByteBuffer frame, int timestamp) {
            }
        });
        Thread.sleep(500);
        dev.stopVideo();


    }

    public void testDepth() throws InterruptedException {


        final Object lock = new Object();
        final long start = System.nanoTime();
        dev.startDepth(new DepthHandler() {

            int frameCount = 0;

            @Override
            public void onFrameReceived(FrameMode mode, ByteBuffer frame, int timestamp) {
                frameCount++;
                if (frameCount == 30) {
                    synchronized (lock) {
                        lock.notify();
                        System.out.format("Got %d depth frames in %4.2fs%n", frameCount,
                                (((double) System.nanoTime() - start) / 1000000000));
                    }
                }
            }
        });
        synchronized (lock) {
            lock.wait(2000);
        }
    }

    public void testVideo() throws InterruptedException {


        final Object lock = new Object();
        final long start = System.nanoTime();
        
        
        //final BufferedImage depth = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        dev.startVideo(new VideoHandler() {

            int frameCount = 0;

            @Override
            public void onFrameReceived(FrameMode mode, ByteBuffer frame, int timestamp) {
                frameCount++;
                if (frameCount == 1) {
                    synchronized (lock) {
                        lock.notify();
                        int width = mode.getWidth(), height = mode.getHeight();
                        BufferedImage color = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);



                        System.out.format("Got %d video frames in %4.2fs%n", frameCount,
                                (((double) System.nanoTime() - start) / 1000000000));
                        
                        long begin = System.nanoTime();
                        

                        
                        for (int y = 0; y < height; y++) {
                            for (int x = 0; x < width; x++) {
                                int offset = 3 * (y * width + x);

                                int r = frame.get(offset + 2) & 0xFF;
                                int g = frame.get(offset + 1) & 0xFF;
                                int b = frame.get(offset + 0) & 0xFF;

                                int pixel = (0xFF) << 24
                                        | (b & 0xFF) << 16
                                        | (g & 0xFF) << 8
                                        | (r & 0xFF);
                                
                                color.setRGB(x, y, pixel);
                            }
                        }
                        System.out.format("Converted to image in %4.2fs%n", 
                                (((double) System.nanoTime() - begin) / 1000000000));
                        
                        begin = System.nanoTime();
                        try {
                            ImageIO.write(color, "jpg", new File("kinect.color.jpg"));
                            System.out.format("Wrote file in %4.2fs%n", 
                                (((double) System.nanoTime() - begin) / 1000000000));
                        } catch (IOException ex) {
                            Logger.getLogger(FreenectTest.class.getName()).log(Level.SEVERE, null, ex);
                        }


                    }
                }
            }
        });
        synchronized (lock) {
            lock.wait(2000);
        }
    }
}