/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinect;
import org.openkinect.freenect.Context;
import org.openkinect.freenect.Device;
import org.openkinect.freenect.Freenect;

/**
 *
 * @author michael
 */
 
public class Kinect {
 
    public static void main(String[] args) throws InterruptedException {
 
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
	        dev.setTiltAngle(20);
	        Thread.sleep(4000);
	        dev.setTiltAngle(-20);
	        Thread.sleep(4000);
	        dev.setTiltAngle(0);
 
	    // SHUT DOWN
	        if (ctx != null)
	            if (dev != null) {
	                dev.close();
	            }
	        ctx.shutdown();
    }
 
}