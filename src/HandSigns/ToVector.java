package HandSigns;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ToVector {
	private static final String Read = "./src/¿â/word2.bmp";
	private static int pixel = 60;
	private static int[][]pixels; 
	public ToVector() {
		// TODO Auto-generated constructor stub
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(new File(Read));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pixels = new int[pixel][pixel];
		for(int i=0;i<pixel;i++){
			for(int j=0;j<pixel;j++){
				int RGB = bufferedImage.getRGB(j, i);
                int R = (RGB & 0xff0000) >> 16;  
                int G = (RGB & 0xff00) >> 8;  
                int B = (RGB & 0xff);
                if(R<=200 && B <=200 && G <=200) pixels[i][j] = 1;
               System.out.print(pixels[i][j]);
			}
			System.out.println("");
		}		
	}
	public int[][] getVector(){
		return pixels;		
	}

}
