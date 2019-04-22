package com.asl.to.text;

import java.util.List;

import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

public class Util {
	
	MatOfPoint getMaxContour(List<MatOfPoint> contours)
	{
		MatOfPoint maxC = null;
		double maxArea = 200;
		for (MatOfPoint mp : contours) {
			double area = Imgproc.contourArea(mp);
			if(area > maxArea) {
				maxArea = area;
				maxC = mp;
			}
		}
		return maxC;
	}
	
	
}
