package lr2forkjoin;

import java.awt.image.BufferedImage;
import java.util.concurrent.RecursiveAction;

public class HandleImage extends RecursiveAction {

    int xStart;
    int yStart;
    int xFinish;
    int yFinish;
    int threshold;

    BufferedImage inputImage;
    BufferedImage outputImage;

    public HandleImage(int xStart, int yStart, int xFinish, int yFinish, int threshold, BufferedImage inputImage, BufferedImage outputImage) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xFinish = xFinish;
        this.yFinish = yFinish;
        this.threshold = threshold;
        this.inputImage = inputImage;
        this.outputImage = outputImage;
    }

    public HandleImage() {
    }

    @Override
    protected void compute() {
        if ((xFinish - xStart) * (yFinish - yStart) <= threshold) {
            computeDirectly();
        } else {
            int lenghtX = xFinish - xStart;
            int lenghtY = yFinish - yStart;
            int offsetX = lenghtX / 2 + xStart;
            int offsetY = lenghtY / 2 + yStart;
            invokeAll(new HandleImage(xStart, yStart, offsetX, offsetY, threshold, inputImage, outputImage),
                    new HandleImage(xStart, offsetY, offsetX, yFinish, threshold, inputImage, outputImage),
                    new HandleImage(offsetX, yStart, xFinish, offsetY, threshold, inputImage, outputImage),
                    new HandleImage(offsetX, offsetY, xFinish, yFinish, threshold, inputImage, outputImage));
        }
    }
    /*Изменение цвета изображения*/
    private void computeDirectly() {
        for (int i = xStart; i < xFinish; i++) {
            for (int j = yStart; j < yFinish; j++) {
                int pixel = inputImage.getRGB(i, j);
                int pixelB = pixel & 0xff;
                int pixelG = (pixel & 0xff00) >> 8;
                int pixelR = (pixel & 0xff000) >> 16;
                pixel = (int) Math.round((0.07*pixelB+0.71*pixelG+0.21*pixelR));
                outputImage.setRGB(i, j, pixel);
            }
        }
    }
}