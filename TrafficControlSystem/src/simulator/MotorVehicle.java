package simulator;

import java.awt.Graphics;

import org.opencv.core.Point;

import observer.TrafficUpdateObservable;
import tracking.Track;

public abstract class MotorVehicle {
	protected double x;
	protected double y;
	protected int lane;
	protected double speed;
	public enum Route {STRAIGHT, LEFT, RIGHT};
	public enum Direction {NORTH, SOUTH, EAST, WEST};
	protected Direction direction;
	Track track;

	//constructor
	public MotorVehicle(int lane, Direction dir, Track track){
		this.lane = lane;
		this.direction = dir;
		this.track = track;

		speed = 0.5f;
	}
	
	//methods
	
	public void updateTrack(Track track)
	{
		this.track = track;
		
		updateLane(track.lane);
		
		updateTrackPosition();
	}
	
	public abstract void updateTrackPosition();
	
	public int getLane(){
		//return lane;	
		return lane;
	}
	
	public void notifyObservers()
	{
		TrafficUpdateObservable.getInstance().updateTraffic(track.track_id, distToIntersection());
	}
	
	
	public abstract void initLane(int lane);
	
	public abstract void updateLane(int lane);
	
	public double distToIntersection()
	{
		switch (direction)
		{
			case NORTH:
				return y - (Config.simDisplayHeight - Config.roadStripLength);
			case SOUTH:
				return Config.roadStripLength - y;
			case EAST:
				return Config.roadStripLength - x;
			case WEST:
				return x - (Config.simDisplayWidth - Config.roadStripLength);
		}
		
		return -99999;
	}
	
	public Direction getDirection()
	{
		return direction;
	}
	
	public abstract void tick();
	
	public void leftTurn(){
		
	}
	
	public void rightTurn(){
		
	}
	
	public void render(Graphics g){
		g.drawImage(loadImage.upCarImage, (int)x, (int)y, 30, 45, null);
	}
	
	protected Point getLaneStartPoint()
	{
		return Config.laneStartPoints[direction.ordinal()][lane];
	}
}