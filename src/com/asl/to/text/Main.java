package com.asl.to.text;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class Main {
	
	static BufferedImage Mat2BufferedImage(Mat matrix)throws Exception {        
	    MatOfByte mob=new MatOfByte();
	    Imgcodecs.imencode(".jpg", matrix, mob);
	    byte ba[]=mob.toArray();

	    BufferedImage bi=ImageIO.read(new ByteArrayInputStream(ba));
	    return bi;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat mat = new Mat();
		VideoCapture camera = new VideoCapture(0);
		JFrame frame = new JFrame("Testing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel();
		frame.setContentPane(label);
		frame.setVisible(true);
		frame.setSize(new Dimension(640,480));
		while(true) {
			if(camera.read(mat)) {
				Mat dst=new Mat();
				Core.flip(mat, dst, 1);
				Imgproc.rectangle(dst, new Point(50,100),new Point(300,450), new Scalar(255,0,134),2);
				Mat dst1 = dst.submat(new Rect(50,100,250,350));
				System.out.println(dst.size().height + " " + dst.size().width);
				Imgproc.cvtColor(dst, dst1, Imgproc.COLOR_RGB2YCrCb);
				Mat blur = new Mat();
				Imgproc.GaussianBlur(dst1, blur, new Size(11,11), 0);
				Mat inrange = new Mat();
				Core.inRange(blur, new Scalar(0,138,67), new Scalar(255,173,133), inrange);
				List<MatOfPoint> contours = new LinkedList<>();
				Mat hierarchy = new Mat();
				Imgproc.findContours(inrange, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
				
				ImageIcon icon = new ImageIcon(Mat2BufferedImage(inrange));
				label.setIcon(icon);
				label.repaint();
			}
		}
	}

}