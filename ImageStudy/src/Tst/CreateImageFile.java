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
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;

public class CreateImageFile {
	BufferedImage image;

	public void graphicsGeneration(String str, String fontPath, String imgurl, int fontSize) throws Exception {
		File file = new File(fontPath);
		// Font dynamicFontPt = new Font("宋体", Font.TRUETYPE_FONT, 180);
		Font dynamicFontPt = new Font("宋体", Font.PLAIN, fontSize);
		if (file.exists()) {
			FileInputStream aixing = new FileInputStream(file);
			Font font = Font.createFont(Font.TRUETYPE_FONT, aixing);
			dynamicFontPt = font.deriveFont((float) fontSize);
		}

		Rectangle2D r = dynamicFontPt.getStringBounds(str,
				new FontRenderContext(AffineTransform.getScaleInstance(1, 1), false, false));

		// int unitHeight = (int) Math.round(r.getHeight());
		// int unitWidth = (int) Math.round(r.getWidth());
		// int width = Math.max(unitWidth, unitHeight);
		int width = fontSize;
		int height = width;
		int adjustY = (int) Math.floor(r.getHeight() - height) - 1;
		System.out.println(String.format("FontSize:%d;GetHeight:%f;GetWidth:%f;width:%d;height:%d;adjustY:%d", fontSize,
				r.getHeight(), r.getWidth(), width, height, adjustY));

		// image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		Graphics graphics = image.getGraphics();

		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(Color.black);
		graphics.setFont(dynamicFontPt);

		// if (width=255 && height=255) adjustY = 40;
		graphics.drawString(str, 0, dynamicFontPt.getSize() - adjustY);
		graphics.dispose();
		FileOutputStream fos = new FileOutputStream(imgurl);
		ImageIO.write(image, "PNG", fos);
	}

	public static void main(String[] args) {
		CreateImageFile cg = new CreateImageFile();
		try {
			String strFolder = new java.io.File(".").getCanonicalPath();
			System.out.println("Current dir using File:" + strFolder);
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
			System.out.println("java.class.path:" + System.getProperty("java.class.path"));
			String desktopPath = System.getProperty("user.home") + "\\Desktop";
			System.out.println("Desktop:" + desktopPath);
			String outputFolder = strFolder + File.separator + "OutputPic";
			File fiOutputFolder = new File(outputFolder);

			for (File file : fiOutputFolder.listFiles())
				if (!file.isDirectory())
					file.delete();

			if (!fiOutputFolder.exists())
				fiOutputFolder.mkdirs();

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

			List<String> lstGenerate = new ArrayList<>();
			// list.forEach(System.out::println);
			int indexName = 1;
			for (String strLine : list) {
				System.out.println(strLine);
				for (char ch : strLine.toCharArray()) {
					String str = String.valueOf(ch);
					System.out.println(str);
					if (!isChinese(ch))
						continue;
					String punctutations = ".,:; ：，。? ";
					if (punctutations.contains(str))
						continue;
					if (lstGenerate.contains(str))
						continue;
					lstGenerate.add(str);
					// cg.graphicsGeneration(str, strFolder + File.separator +
					// "启功字体繁体.ttf",
					// outputFolder + File.separator + String.valueOf(indexName)
					// + "0.jpg");
					// for (int fontsize = 20; fontsize <= 50; fontsize += 10) {
					for (int fontsize = 20; fontsize <= 20; fontsize += 10) {
						String exportFileNameX = String.format("%d_10_%d.png", indexName, fontsize);
						String exportFileNameY = String.format("%d_30_%d.png", indexName, fontsize);
						// if indexName % 3 ==0, then as test
						if (indexName % 5 == 0) {
							exportFileNameX = String.format("%d_11_%d.png", indexName, fontsize);
							exportFileNameY = String.format("%d_31_%d.png", indexName, fontsize);
						}

						cg.graphicsGeneration(str, strFolder + File.separator + "启功字体简体.TTF",
								outputFolder + File.separator + exportFileNameX, fontsize);

						cg.graphicsGeneration(str, "", outputFolder + File.separator + exportFileNameY, fontsize);
					}
					indexName++;
				}
			}

			System.out.println("Generate OK.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * check if Chinese character
	 * 
	 * @param a
	 *            char
	 * @return boolean
	 */
	public static boolean isChinese(char a) {
		int v = (int) a;
		return (v >= 19968 && v <= 171941);
	}
}
