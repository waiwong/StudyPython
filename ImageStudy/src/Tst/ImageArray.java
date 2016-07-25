package Tst;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

// For reading images from files.
import javax.imageio.*;
import java.io.*;

// For reading image over network
import java.net.*;


public class ImageArray extends Frame implements WindowListener 
{
	double colorImage[][][];
	
	// main is called when the program starts
	public static void main(String args[]) 
	{
		// setup our application window
		ImageArray app = new ImageArray();
		
		// Get the image off of the internet that we want to use
		app.colorImage = app.readImageFile("C:\\Users\\weiwang\\workspace\\ImageStudy\\0.jpg");
		
		// If the file is on your computer, comment out the line above
		// and use the one below instead (change the path appropriately)!
		// Use \\ whenever you want to use a \
		
		// app.colorImage = app.readImageFile("X:\\shed.jpg");
		
		app.setSize(600, 600);
		app.setVisible(true);
		app.addWindowListener(app);
	}
	
	// paint is called when Java needs to repaint the window
	public void paint(Graphics g) 
	{	
		// This creates a new 2D array of doubles that is 100 wide and 
		// 100 tall.  It will be a grayscale image where 0=black and 1=white.
		// It is set to 0 (black) initially.

		int width=100;
		int height=100;
		double image[][] = new double[width][height];
		
		// The coordinate system inside of the image uses the same system as a Java applet:
		// image[0][0] is the top left pixel
		// image[10][20] is the pixel in the 11th column and 20th row.
		
		// Set every pixel with y<50 to white. 
		for(int x=0; x<width; x++)
			for(int y=0; y<50; y++)
				image[x][y]=1;  

		// This draws the image represented in our array.
		// The top left corner of the image will be at 0,50 
		// on the screen.
		g.drawImage(createImage(image), 0, 50, null);
		
		// ******************
		// **** STEP 1: *****
		// ******************
		// Draw an image that puts a random value (0-1) in each pixel
		
		image = new double[width][height];  // create a new array that is all black
		for(int x=0; x<width; x++)       // loop over each pixel
			for(int y=0; y<height; y++)
				image[x][y]= Math.random();   // MODIFY THIS LINE OF CODE
		g.drawImage(createImage(image), 100, 50, null);
		
		
		// ******************
		// **** STEP 2: *****
		// ******************
		// Make every other horizontal line white.
		// Put a if statement inside of the loops to do this!
		// Remember (i%2==0) will be true if i is even.  
		image = new double[width][height];
		for(int x=0; x<width; x++)
			for(int y=0; y<height; y++)
				if(y%2==0)
				image[x][y]=0;// YOUR IF STATEMENT WILL GO HERE!
				else
				image[x][y]=1;
				g.drawImage(createImage(image), 200, 50, null);
			

		// ******************
		// **** STEP 3: *****
		// ******************
		// Make every other vertical line white.
		// Write all of the code yourself!
		// Draw the image at coordinate 300,50
		image = new double[width][height];
		for(int x=0; x<width; x++)
			for(int y=0; y<height; y++)
				if(x%2==0)
				image[x][y]=0;// YOUR IF STATEMENT WILL GO HERE!
				else
				image[x][y]=1;
				g.drawImage(createImage(image), 300, 50, null);
		
		
		// ******************
		// **** STEP 4: *****
		// ******************
		// Go to http://www.eng.utah.edu/~cs1021/images
		// Find a different image to load.
		// Change the URL at the top of this
		// file so that it loads a different image.
		
		for(int x=0;x<colorImage.length;x++)
			for(int y=0;y<colorImage[0].length;x++)
			{
				colorImage[x][y][0]=1;
				colorImage[x][y][1]=1;
				colorImage[x][y][2]=1;
			}
				
		g.drawImage(createImage(colorImage), 0, 150, null);

	
		// *********************
		// **** FINISIHED? *****
		// *********************
		// * The color image we loaded from a file is a 3D array
		//   named colorImage.
		//   The last dimension is a color.  If you loop across
		//   the width and height of the image with two loops
		//   there are three values you can change:
		//   colorImage[x][y][0] // red
		//   colorImage[x][y][1] // green
		//   colorImage[x][y][2] // blue
		//
		// * Try tinting the color image by setting some
		//   of the values to 0 or 1 for the entire image.
		//   Put the code that tints the image above the
		//   code that draws it on the screen!
		//
		// * Start on homework!
	}

	
	// **************************************************
	// **** DON'T CHANGE THE CODE BELOW THIS POINT! *****
	// **************************************************

	public void windowActivated(WindowEvent e) { }
	public void windowDeactivated(WindowEvent e) { }
	public void windowOpened(WindowEvent e) { }
	public void windowClosed(WindowEvent e) { }
	public void windowClosing(WindowEvent e) { System.exit(0); }
	public void windowIconified(WindowEvent e) { }
	public void windowDeiconified(WindowEvent e) { }
	

	// Given a filename, reads in the image and stores it in an array of doubles.
	public double[][][] readImageFile(String filename)
	{
		BufferedImage image = null;
		System.out.println("Please wait...loading image...");
		System.out.flush();
		try
		{
			 image = ImageIO.read(new File(filename));
		}
		catch(Exception e)
		{
			try
			{
				image = ImageIO.read(new URL(filename));
			}
			catch(Exception ex)
			{
				// Return an image with one black pixel if error.
				System.out.println("Couldn't read file " + filename );
				double ret[][][]= new double[1][1][3];
				ret[0][0][0]=0;
				ret[0][0][1]=0;
				ret[0][0][2]=0;
				return ret;
				// System.exit(1);	
			}
		}
			
		System.out.println("Finished reading a " + image.getWidth() + "x" + image.getHeight());
		
		double ret[][][] = new double[image.getWidth()][image.getHeight()][3];
		int oneD[] = image.getRGB(0,0,image.getWidth(),image.getHeight(),null,0,image.getWidth());
		for(int i=0; i<image.getHeight(); i++)
			for(int j=0; j<image.getWidth(); j++)
				for(int k=2;k>=0; k--)
				{
					ret[j][i][k] = (oneD[i*image.getWidth()+j] & 0x000000FF)/255.0;
					oneD[i*image.getWidth()+j] = oneD[i*image.getWidth()+j] >> 8; 
				}
				
		
		return ret;
	}
	
	
	/* Creates a grayscale image from a 2D array of doubles.  0=black, 1=white */
	public BufferedImage createImage(double image[][])
	{
		// Create the 2D array of ints that we will fill in		
		int imageInt[][] = new int[image.length][];
		
		// For each row
		for(int i=0; i<image.length; i++)
		{
			// Create the row
			imageInt[i]=new int[image[i].length];
			// For each pixel in the row
			for(int j=0; j<image[i].length; j++)
				// Convert it to an int 0--255.
				imageInt[i][j]=(int)Math.round(image[i][j]*255);
		}
		
		// Create an image from the integer array we made and return the image.
		return createImage(imageInt);
	}
	
	
	/* Creates an image from a 3D array of doubles.  0=black, 1=white */
	public BufferedImage createImage(double image[][][])
	{
		// Create the 2D array of ints that we will fill in		
		int imageInt[][][] = new int[image.length][][];
		
		// For each row
		for(int i=0; i<image.length; i++)
		{
			// Create the row
			imageInt[i]=new int[image[i].length][];

			// For each pixel in the row
			for(int j=0; j<image[i].length; j++)
			{
				// Create the pixel
				imageInt[i][j]=new int[3];
				
				// for each color in the pixel
				for(int k=0; k<3; k++)
					// Convert it to an int 0--255.
					imageInt[i][j][k]=(int)Math.round(image[i][j][k]*255);
			}
		}
		
		// Create an image from the integer array we made and return the image.
		return createImage(imageInt);
	}
	
	// Creates a grayscale image from an array of ints.  0=black, 255=white.
	public BufferedImage createImage(int image[][])
	{
		int width = image.length;
		int height = image[0].length;
				
		// Convert the 2D data into a 1D array:
		int oneD[] = new int[width*height];
		// For each row
		for(int i=0; i<width; i++)
		{
			// Check if this row has the correct number of pixels
			if(image[i].length != height)
			{
				System.out.println("Image is not rectangular");
				return null;
			}

			// for each column
			for(int j=0; j<height; j++)
			{
				// check for invalid values
				if(image[i][j] > 255)
					image[i][j]=255;
				else if(image[i][j] < 0)
					image[i][j]=0;
				
				// Combine each pixel into one int.
				// First 8 bits are alpha value (255=opaque)
				// Then red, green, and blue (8-bits each)
				oneD[j*width+i] = 0xFF000000 |
				  (0x000000FF & image[i][j]) << 16 |
				  (0x000000FF & image[i][j]) << 8 |
				  (0x000000FF & image[i][j]);
			}
		}
		
		
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		img.setRGB(0,0,width,height,oneD,0,width);
		return img;
	}
	
	// Creates a color image from an array of ints.  0=off, 255=on.
	public BufferedImage createImage(int image[][][])
	{
		int width = image.length;
		int height = image[0].length;
						
		// Convert the 2D data into a 3D array:
		int oneD[] = new int[width*height];
		// For column
		for(int i=0; i<width; i++)
		{
			// Check if column has the correct number of pixels
			if(image[i].length != height)
			{
				System.out.println("Image is not rectangular");
				return null;
			}

			// for each row
			for(int j=0; j<height; j++)
			{
				if(image[i][j].length!=3)
				{
					System.out.println("Image does not have 3 colors");
					return null;
				}

				// Check for invalid values.
				for(int k=0; k<3; k++)
					if(image[i][j][k] > 255)
						image[i][j][k] = 255;
					else if(image[i][j][k] < 0)
						image[i][j][k] = 0;

				
				// Combine each pixel into one int.
				// First 8 bits are alpha value (255=opaque)
				// Then red, green, and blue (8-bits each)
				oneD[j*width+i] = 0xFF000000 |
								  (0x000000FF & image[i][j][0]) << 16 |
								  (0x000000FF & image[i][j][1]) << 8 |
								  (0x000000FF & image[i][j][2]);
			}
		}
		
		
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		img.setRGB(0,0,width,height,oneD,0,width);
		return img;
	}

}