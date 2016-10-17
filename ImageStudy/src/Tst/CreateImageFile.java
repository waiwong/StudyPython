package Tst;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;

public class CreateImageFile {
	BufferedImage image;

	public void graphicsGeneration(String str, String fontPath, String imgurl) throws Exception {
		File file = new File(fontPath);
//		Font dynamicFontPt = new Font("宋体", Font.TRUETYPE_FONT, 180);
		Font dynamicFontPt = new Font("宋体", Font.PLAIN, 180);
		if (file.exists()) {
			FileInputStream aixing = new FileInputStream(file);
			Font font = Font.createFont(Font.TRUETYPE_FONT, aixing);
			dynamicFontPt = font.deriveFont(180f);
		}

		Rectangle2D r = dynamicFontPt.getStringBounds(str,
				new FontRenderContext(AffineTransform.getScaleInstance(1, 1), false, false));

		int unitHeight = (int) Math.floor(r.getHeight());
		int width = (int) Math.round(r.getWidth()) + 8;

		int height = unitHeight + 5;

		// image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics graphics = image.getGraphics();

		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(Color.black);

		graphics.setFont(dynamicFontPt);

		graphics.drawString(str, 5, dynamicFontPt.getSize() + 10);
		graphics.dispose();
		FileOutputStream fos = new FileOutputStream(imgurl);
		ImageIO.write(image, "png", fos);
	}

	public static void main(String[] args) {
		CreateImageFile cg = new CreateImageFile();
		try {
			// String strFolder =
			// "D:\\ProjectFolder\\MyGitRepository\\StudyJava\\ImageStudy";
			String strFolder = new java.io.File(".").getCanonicalPath();
			System.out.println("Current dir using File:" + strFolder);
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
			System.out.println("java.class.path:" + System.getProperty("java.class.path"));
			String desktopPath = System.getProperty("user.home") + "\\Desktop";
			System.out.println("Desktop:" + desktopPath);
			String outputFolder = strFolder + File.separator + "OutputPic";
			(new File(outputFolder)).mkdirs();
			System.out.println("Output folder:" + outputFolder);
			String wordsFileName = strFolder + File.separator + "Words.txt";
			System.out.println("wordsFileName folder:" + wordsFileName);
			List<String> list = new ArrayList<>();
			try (InputStreamReader isr = new InputStreamReader(new FileInputStream(wordsFileName), "UTF-8");
					BufferedReader br = new BufferedReader(isr);) {
				list = br.lines().collect(Collectors.toList());
			} catch (IOException e) {
				e.printStackTrace();
			}

			list.forEach(System.out::println);
			int indexName = 0;
			for (String strLine : list) {
				System.out.println(strLine);
				for (char ch : strLine.toCharArray()) {
					String str = String.valueOf(ch);
					System.out.println(str);
					// cg.graphicsGeneration(str, strFolder + File.separator +
					// "启功字体繁体.ttf",
					// outputFolder + File.separator + String.valueOf(indexName)
					// + "0.jpg");
					cg.graphicsGeneration(str, strFolder + File.separator + "启功字体简体.TTF",
							outputFolder + File.separator + String.valueOf(indexName) + "1.jpg");
					cg.graphicsGeneration(str, "", outputFolder + File.separator + String.valueOf(indexName) + "3.jpg");
					indexName++;
				}
			}

			System.out.println("Generate OK.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
