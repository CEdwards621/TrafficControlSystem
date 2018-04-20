package simulator;

import java.awt.Color;
import java.awt.Graphics;

import simulator.Constants.Direction;
import tracking.Track;

public class NorthboundMotor extends MotorVehicle{

	//constructor
	public NorthboundMotor(int lane, Track track){

		super(lane, Direction.NORTH, track);
		
		initLane(lane);

	}

	@Override
	public void tick() {
	
	}

	@Override
	public void initLane(int lane) {
		// TODO Auto-generated method stub

		if(lane == 1){
			//x = Config.northBoundLane1.x;
			//y = Config.northBoundLane1.y;
		}
		if(lane == 2){
			//x = Config.northBoundLane2.x;
			//y = Config.northBoundLane2.y;
		}
	}

	@Override
	public void updateLane(int lane) {
		// TODO Auto-generated method stub
		if(lane == 1){
			x = 320;
		}
		if(lane == 2){
			x = 355;
		}
	}

	@Override
	public void updateTrackPosition() {
		// TODO Auto-generated method stub
		y = SimConfig.simDisplayHeight - (track.getBestPositionCenter().y * SimConfig.roadStripRatio);
	}
	
	/*public void render(Graphics g){
		g.drawImage(loadImage.upCarImage, (int)x, (int)y, 30, 45, null);	
	}*/
	
	//methods
}
