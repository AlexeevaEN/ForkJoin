package lr2forkjoin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
public class LR2ForkJoin {

    public static void main(String[] args) {
        try {
            BufferedImage inputImage = null;
            inputImage = ImageIO.read(new File("example.jpg"));
            BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), inputImage.getType());
            
            ForkJoinPool pool = new ForkJoinPool();
            HandleImage hi = new HandleImage(0, 0, inputImage.getWidth()-1, inputImage.getHeight()-1, 100000, inputImage, outputImage);
            pool.invoke(hi);
                        
            File outputfile = new File("output.jpg");
            ImageIO.write(outputImage, "jpg", outputfile);

        } catch (IOException ex) {
            Logger.getLogger(LR2ForkJoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
