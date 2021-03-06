package application;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
//import java.util.*;
//import java.lang.Runnable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import config.TrafficControllerConfig;
import observer.TrafficLightObservable;
import observer.TrafficLightObserver;
import simulator.Constants.Direction;

public class TrafficLight implements TrafficLightObservable {
	//public enum SignalColor { Green, Yellow, Red }
	
	//private ExecutorService executor = Executors.newSingleThreadExecutor();
	private ReadWriteLock rwLock = new ReentrantReadWriteLock();
	
	private static int lightCounter = 0; // increment for each TrafficLight object created, 1st gets id of 1
	private int id = 0;
	public int getID() { return this.id; }
	
	private Direction forTravelDirection = Direction.NORTH;
	public Direction getTravelDirection() { return forTravelDirection; }
	
	private Direction facingDirection = Direction.NORTH;
	public Direction getFacingDirection() { return facingDirection; }
	
	private BulbColor color = BulbColor.Red;
	public BulbColor GetColor() { return this.color; }
	
	private Instant lastChanged = Instant.now();
	public Instant getLastChanged() { return lastChanged; }
	
	private ArrayList<TrafficLightObserver> observers = new ArrayList<TrafficLightObserver>();
	
	public TrafficLight(Direction forTravelDirection) {
		this.id = ++lightCounter;
		this.color = BulbColor.Red;
		this.forTravelDirection = forTravelDirection;
		this.facingDirection = 
			  this.forTravelDirection == Direction.NORTH	? Direction.SOUTH
			: this.forTravelDirection == Direction.SOUTH	? Direction.NORTH
			: this.forTravelDirection == Direction.EAST		? Direction.WEST
			: Direction.EAST;
		
		//create simulator light
		log(0, "Light %04d created for travel direction %s, color %s", this.id, forTravelDirection.toString(), this.color.toString());
	}
	
	/* observeable methods */
	public void addObserver(TrafficLightObserver o) {
		observers.add(o);
		log(0, "Light %04d added observer %s", this.id, o.toString());
	}
	
	public void removeObserver(TrafficLightObserver o) {
		observers.remove(o);
		log(0, "Light %04d removed observer %s", this.id, o.toString());
	}
	
	public void notifyObservers() {
		log(5, "notifyObservers ENTER");
		for (TrafficLightObserver observer : observers) {
			try {
				observer.update(this);
			}
			catch (Exception ex) { ex.printStackTrace(); }
		}
		log(5, "notifyObservers LEAVE");
	}
	/* end of observable methods */
	
	// change the light to green only if it's red
	public void TurnGreen() {
		log(5, "TurnGreen ENTER");
		if (this.color == BulbColor.Yellow) {
			WaitUntilColor(BulbColor.Red, TrafficControllerConfig.secondsYellowLightDuration);
		}
		if (this.color == BulbColor.Red) {
			try {
				rwLock.writeLock().lock();
				this.color = BulbColor.Green;
				logColorState();
				this.lastChanged = Instant.now();
			}
			catch (Exception ex) { ex.printStackTrace(); }
			finally {
				rwLock.writeLock().unlock();
			}
			notifyObservers();
		}
		log(5, "TurnGreen LEAVE");
	}
	
	// cycle the light from green to yellow, pause, then change to red
	/*TODO (DONE): downgrade writelock to readlock to allow clients to query yellow status. Because you can't upgrade a lock from read to write, would
	 * need to release read lock grab new write lock, change to red, then unlock.*/
	public void TurnRed() {
		log(5, "TurnRed ENTER");
		if (this.color == BulbColor.Green) {
			try {
				// grab writelock for light change
				rwLock.writeLock().lock();
				this.color = BulbColor.Yellow;
				logColorState();
				this.lastChanged = Instant.now();
				// downgrade to readlock, lets clients view signal color change
				rwLock.readLock().lock();
				rwLock.writeLock().unlock();
				notifyObservers();
				try {
					TimeUnit.SECONDS.sleep((long)TrafficControllerConfig.secondsYellowLightDuration);
				}
				catch (Exception ex2) { ex2.printStackTrace(); }
				// upgrade to writelock for light change
				rwLock.readLock().unlock();
				rwLock.writeLock().lock();
				this.color = BulbColor.Red;
				logColorState();
				this.lastChanged = Instant.now();
			}
			catch (Exception ex) { ex.printStackTrace(); }
			finally {
				rwLock.writeLock().unlock();
			}
			notifyObservers();
		}
		log(5, "TurnRed LEAVE");
	}
	
	// if light is not not requested color, wait up to N seconds for it to become that color, then return
	private void WaitUntilColor(BulbColor color, long maxSeconds) {
		log(5, "WaitUntilColor ENTER");
		Instant loopStart = Instant.now();
		while (this.color != color && ChronoUnit.SECONDS.between(loopStart, Instant.now()) <= maxSeconds) {
			try {
				TimeUnit.MILLISECONDS.sleep((long)100);
			} catch (InterruptedException e) { e.printStackTrace(); }
		}
		log(5, "WaitUntilColor LEAVE");
	}
	
	// return true if this light's color has remained unchanged (i.e. owned) for its min duration
//	public boolean IsMinOwnershipDurationElapsed() {
//		return secondsSinceLastChange() >= Config.minSecondsOwnershipUntilChangeAllowed;
//	}
	
	// return number of seconds since the light last changed color
	public long secondsSinceLastChange() {
		return ChronoUnit.SECONDS.between(lastChanged, Instant.now());
	}
	
	// log the current state of the traffic light  
	public void logColorState() {
		log(0, "Light %04d: travel direction %s, is %s", this.id, this.forTravelDirection.toString(), this.color.toString());
	}
	
	private void log(int eventImportance, String format, Object ... args) {
		if (eventImportance <= TrafficControllerConfig.loggingLevel) {
			System.out.println(String.format("%s %04d: %s %s", "TrafficLight", this.id, Instant.now().toString(), String.format(format, args)));
		}
	}
}
