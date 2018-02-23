package simulator;

import java.awt.Graphics;

import tracking.Track;

public class WestboundMotor extends MotorVehicle{
	
	//constructor
	public WestboundMotor(int lane, Track track){
		
		super(lane, Direction.WEST, track);
	
		initLane(lane);
	}
	
	@Override
	public void tick() {
		x -= track.getDistChange().x;
	}

	@Override
	public void initLane(int lane) {
		// TODO Auto-generated method stub

		if(lane == 1){
			x = 600;
			y = 245;
		}
		if(lane == 2){
			x = 600;
			y = 210;
		}
	}

	@Override
	public void updateLane(int lane) {
		// TODO Auto-generated method stub
		if(lane == 1){
			y = 245;
		}
		if(lane == 2){
			y = 210;
		}
	}
	
	//methods
}


