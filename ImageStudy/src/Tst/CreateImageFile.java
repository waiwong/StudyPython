package Tst;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

public class CreateImageFile {
	BufferedImage image;

	public void graphicsGeneration(String fontPath, String imgurl) throws Exception {

		String str = "维";

		File file = new File(fontPath);
		FileInputStream aixing = new FileInputStream(file);
		Font font = Font.createFont(Font.TRUETYPE_FONT, aixing);
		Font dynamicFontPt = font.deriveFont(120f);

		Rectangle2D r = dynamicFontPt.getStringBounds(str,
				new FontRenderContext(AffineTransform.getScaleInstance(1, 1), false, false));

		int unitHeight = (int) Math.floor(r.getHeight());

		int width = (int) Math.round(r.getWidth()) + 20;

		int height = unitHeight + 10;

		//image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics graphics = image.getGraphics();
		
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(Color.black);
		
		graphics.setFont(dynamicFontPt);
		
		graphics.drawString(str, 10, dynamicFontPt.getSize() + 10);
		graphics.dispose();
		FileOutputStream fos = new FileOutputStream(imgurl);
		ImageIO.write(image, "png", fos);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CreateImageFile cg = new CreateImageFile();
		try {
			cg.graphicsGeneration("C:\\Users\\weiwang\\workspace\\ImageStudy\\启功字体繁体.ttf",
					"C:\\Users\\weiwang\\workspace\\ImageStudy\\0.jpg");
			cg.graphicsGeneration("C:\\Users\\weiwang\\workspace\\ImageStudy\\启功字体简体.TTF",
					"C:\\Users\\weiwang\\workspace\\ImageStudy\\1.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
