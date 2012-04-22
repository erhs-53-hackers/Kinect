/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinect;
import java.nio.ByteBuffer;
import org.openkinect.freenect.*;
/**
 *
 * @author michael
 */
public class SimpleDepthHandler implements DepthHandler{

    @Override
    public void onFrameReceived(FrameMode fm, ByteBuffer bb, int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
