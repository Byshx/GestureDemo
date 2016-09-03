package HandSigns;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GetToSame {
	private static final String ReadPic = "./src/¿â/word.bmp";
	private static final String OutPutPic = "./src/¿â/word2.bmp";
	private static final String OutPutPic2 = "./src/¿â/word2.jpg";
	private static int pixel = 60;

	public GetToSame() {
		// TODO Auto-generated constructor stub
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(new File(ReadPic));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage result = new BufferedImage(pixel, pixel, BufferedImage.TYPE_INT_RGB);
		result.getGraphics().drawImage(bufferedImage.getScaledInstance(pixel, pixel, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		try {
			ImageIO.write(result, "BMP", new File(OutPutPic));
			ImageIO.write(result, "JPG", new File(OutPutPic2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
