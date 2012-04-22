/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinect;
import org.openkinect.freenect.*;

/**
 *
 * @author michael
 */
 
public class Kinect {
 
    public static void main(String[] args) throws InterruptedException {
        FreenectTest test = new FreenectTest();
        FreenectTest.initKinect();
        FreenectTest.dev.setTiltAngle(30);
        Thread.sleep(2000);
        test.testVideo();
        //test.testDepth();
        Thread.sleep(5000);
        
        FreenectTest.shutdownKinect();
        /*
 
    	// DECLARATIONS
	        Context ctx = null;
	        Device dev = null;
 
    	// INITIALIZE DEVICE
	        ctx = Freenect.createContext();
	        if (ctx.numDevices() > 0) {
	            dev = ctx.openDevice(0);
	        } else {
	            System.err.println("No kinects detected.  Exiting.");
	            System.exit(0);
	        }
 
	    // TILT UP, DOWN, & RETURN
	        //dev.setTiltAngle(20);
	        //Thread.sleep(4000);
	        //dev.setTiltAngle(-20);
	        //Thread.sleep(4000);
               /*
                dev.setLed(LedStatus.BLINK_RED_YELLOW);
                while(true) {
	        dev.setTiltAngle(0);
                //Thread.sleep(4000);
                //dev.setTiltAngle(-27);
                Thread.sleep(100); 
                }
               
                               
                dev.setLed(LedStatus.RED);
                
                
                dev.setVideoFormat(VideoFormat.RGB);
                dev.startVideo(new SimpleVideoHandler());
                Thread.sleep(5000);
                dev.stopVideo();
                
 
	    // SHUT DOWN
                dev.setLed(LedStatus.BLINK_GREEN);
	        if (ctx != null)
	            if (dev != null) {
	                dev.close();
	            }
	        ctx.shutdown();
                * 
                */
               
                
                
                
                
                
                
                
    }
 
}