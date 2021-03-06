package config;

import org.opencv.core.Point;

import application.TrafficController.SignalLogicConfiguration;

public class SimConfig {

	public static boolean runSimulator = true;
	public static boolean startSimulatedMotors = false;
	//public static SignalLogicConfiguration defaultTrafficControllerLogicConfiguration = SignalLogicConfiguration.FixedTimers;
	public static SignalLogicConfiguration defaultTrafficControllerLogicConfiguration = SignalLogicConfiguration.OnDemand;
	
	public static float speed = .2f;
	
	public static double simDisplayWidth = 600;
	public static double simDisplayHeight = 600;
	
	public static double videoDisplayWidth = 950;
	public static double videoDisplayHeight = 750;
	
	public static double frameHeight = 1080;
	public static double frameWidth = 1920;
	
	public static double widthRatio = frameWidth / simDisplayWidth;
	public static double heightRatio = frameHeight / simDisplayHeight;
	
	// length of road before intersection
	public static double roadStripLength = 210;
	public static double roadStripFudgeFactor = 50;
	public static double roadStripFudged = roadStripLength - roadStripFudgeFactor;
	
	public static double roadStripRatio = roadStripFudged / frameHeight;
	
	// lane information on intersection image
	public final static Point eastBoundLane1 = new Point(-50, 320);
	public final static Point eastBoundLane2 = new Point(-50, 355);
	
	public final static Point westBoundLane1 = new Point(600, 245);
	public final static Point westBoundLane2 = new Point(600, 210);
	
	public final static Point southBoundLane1 = new Point(210, -50);
	public final static Point southBoundLane2 = new Point(250, -50);
	
	public final static Point northBoundLane1 = new Point(320, 600);
	public final static Point northBoundLane2 = new Point(355, 600);
	
	public final static Point[][] laneStartPoints = new Point[][] {
		{northBoundLane1, northBoundLane2},
		{southBoundLane1, southBoundLane2},
		{eastBoundLane1, eastBoundLane2},
		{westBoundLane1, westBoundLane2}
	};
	
	// Traffic Light Bulb locations & parameters
	public static double bulbHeight = 40;
	public static double bulbWidth = 40;
	public static double bulbDistance = 30;
	//red light bulb points
	public static Point northRLight = new Point (382, 380);
	public static Point northYLight = new Point (382, 410);
	public static Point northGLight = new Point (382, 440);
	public static Point southRLight = new Point (165, 170);
	public static Point southYLight = new Point (165, 140);
	public static Point southGLight = new Point (165, 110);
	public static Point eastRLight = new Point (170, 385);
	public static Point eastYLight = new Point (140, 385);
	public static Point eastGLight = new Point (110, 385);
	public static Point westRLight = new Point (380, 167);
	public static Point westYLight = new Point (410, 167);
	public static Point westGLight = new Point (440, 167);
}
