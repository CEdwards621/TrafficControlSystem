package application;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Lane {

	private Line line;
	
	public Lane(Point p1, Point p2)
	{
		line = new Line(p1, p2);
	}
	
	public Point getCenter()
	{
		return line.getCenter();
	}
	
	public Point getStart()
	{
		return line.getStartPoint();
	}
	
	public Point getEnd()
	{
		return line.getEndPoint();
	}
	
	public void drawLane(Mat frame)
	{

		Imgproc.line(frame, 
				line.getStartPoint(), 
				line.getEndPoint(), 
				new Scalar(0,255,0),
				2);
		
	}
	
}
