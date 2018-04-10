package tracking;

import java.util.ArrayList;

import org.opencv.core.Point;
import org.opencv.core.Rect;

import application.Color;
import application.DetectedObject;
import application.TrafficController;
import application.TrafficLight;
import simulator.MotorVehicle;
import simulator.MotorVehicle.Direction;
import simulator.SimulatorManager;

public class SimulatedTrack extends Track{

	private Point lastPosition;
	private Point newPosition;
	private double speed = 0.05;
	private double elapsedTime = 0.0;
	private Direction travelDirection;

	
	public SimulatedTrack(Point pt, float dt, float Accel_noise_mag, int id, DetectedObject lastUpdate) {
		super(pt, dt, Accel_noise_mag, id, lastUpdate);
		// TODO Auto-generated constructor stub
	}
	
	public SimulatedTrack(Point pt, int id, Direction direction, double speed)
	{
		super(pt, 0, 0, 0, null);
		
		track_id = id;
		travelDirection = direction;
		
		this.speed = speed;
		
		lastPosition = pt;
		newPosition = pt;
		
	}
	
	public Point getDistChange()
	{
		return new Point(lastPosition.x - newPosition.x,
				lastPosition.y - newPosition.y);
	}

	public Point getLastCenter()
	{
		return lastPosition;
	}
	
	public Rect getBestPositionRect()
	{
		Rect r = new Rect();
		
		r.x      = (int)newPosition.x;
		r.y      = (int)newPosition.y;
		r.width  = (int)newPosition.x + 20;
		r.height = (int)newPosition.y + 20;
		
		return r;
	}
	
	public Point getBestPositionCenter()
	{
		return newPosition;
	}
	
	public void setSpeed(double speed)
	{
		this.speed = speed;
	}
	
	public void setPosition(Point p)
	{
		newPosition = p;
	}
	
	/*public void checkLight(MotorVehicle car){
		TrafficLight tl;
		tl = simulator.SimulatorManager.trafficController.GetTrafficLightForVehicle(car);
		Color light = tl.GetColor();
		double oldSpeed;
		oldSpeed = car.getSpeed();
				
			switch(light) {
				case Green:
					break;
				case Yellow:
					car.setSpeed(oldSpeed*0.5);
					break;
				case Red:
					car.setSpeed(0);
					break;
				default:
					break;
				}
	}*/
		
	
	public void updateTrackPosition()
	{
		Point temp = newPosition;
		
		switch (travelDirection)
		{
		case NORTH:
			newPosition.y = lastPosition.y - (speed * getSecSinceUpdate());
			break;
		case SOUTH:
			newPosition.y = lastPosition.y + (speed * getSecSinceUpdate());
			break;
		case EAST:
			newPosition.x = lastPosition.x + (speed * getSecSinceUpdate());
			break;
		case WEST:
			newPosition.x = lastPosition.x - (speed * getSecSinceUpdate());
			break;
		}
		
		lastPosition = temp;
	}

}
