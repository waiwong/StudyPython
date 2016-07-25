package Tst;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CreateImageFileFromGraphicsObject {

	public static void main(String[] args) throws IOException {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("启功字体繁体.ttf")));

			int width = 150;
			int height =150;

			// Constructs a BufferedImage of one of the predefined image types.
			// BufferedImage bufferedImage = new BufferedImage(width, height, 
			// BufferedImage.TYPE_INT_RGB);
			//BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

			// Create a graphics which can be used to draw into the buffered
			// image
			Graphics2D g2d = bufferedImage.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// fill all the image with white
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, width, height);
			
			g2d.setColor(Color.BLACK);
		    g2d.drawRect(0, 0, width, height);

			Font fnt = new Font("启功字体简体", Font.PLAIN, 120);
			g2d.setColor(Color.black);
			g2d.setFont(fnt);

			String message = "维";
			FontMetrics fontMetrics = g2d.getFontMetrics();
			int stringWidth = fontMetrics.stringWidth(message);
			int stringHeight = fontMetrics.getAscent();
			//g2d.drawString(String.valueOf(stringWidth), 10,50);
			//g2d.drawString(String.valueOf(stringHeight), 200,10);
			
			g2d.setPaint(Color.black);
			g2d.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);

			// Disposes of this graphics context and releases any system
			// resources that it is using.
			g2d.dispose();

			// Save as PNG
			File file = new File("10.png");
			ImageIO.write(bufferedImage, "png", file);

			// Save as JPEG
			file = new File("11.jpg");
			ImageIO.write(bufferedImage, "jpg", file);
			
			file = new File("12.bmp");
			ImageIO.write(bufferedImage, "bmp", file);
		} catch (IOException | FontFormatException e) {
			// Handle exception
		}
	}
}
