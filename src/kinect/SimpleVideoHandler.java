/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kinect;

import com.sun.corba.se.pept.transport.InboundConnectionCache;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.openkinect.freenect.FrameMode;
import org.openkinect.freenect.VideoHandler;

/**
 *
 * @author michael
 */
public class SimpleVideoHandler implements VideoHandler{

    @Override
    public void onFrameReceived(FrameMode fm, ByteBuffer bb, int i) {
        //throw new UnsupportedOperationException("Not supported yet.");
        IntBuffer ib = bb.asIntBuffer();
        
        int[] pixels = new int[ib.limit()];
        
        ib.put(pixels);
        System.out.println("width: " + fm.getWidth());
        System.out.println("height: " + fm.getHeight());
        for(int a=0;a<pixels.length;a++) {
            System.out.println(bb.get(a));
        }
        
        /*
        
        BufferedImage image = new BufferedImage(0, 0, BufferedImage.TYPE_INT_ARGB);
        
        image.setRGB(0, 0, 640, 480, pixels, 0, 640);   
        
        //image.s
        
        File file = new File("image.png");
        
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException ex) {
            System.err.println("Error writting image:");
            System.err.println(ex.getMessage());
        }
        * */
        
        
        
        
        
        
        
    }
    
}
