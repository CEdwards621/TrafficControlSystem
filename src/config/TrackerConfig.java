package config;

import org.opencv.core.Scalar;

/**
 * CONFIG.java
 * TODO: 
 *
 * @author Kim Dinh Son
 * Email:sonkdbk@gmail.com
 */

public class TrackerConfig {

	public static Scalar Colors[] = { 
		new Scalar(255, 0, 0), 
		new Scalar(0, 255, 0),
		new Scalar(0, 0, 255), 
		new Scalar(255, 255, 0),
		new Scalar(0, 255, 255), 
		new Scalar(255, 0, 255),
		new Scalar(255, 127, 255), 
		new Scalar(127, 0, 255),
		new Scalar(127, 0, 127) };
	
	public static double learningRate = 0.005;
	
	public static double _dt = 0.2;
	public static double _Accel_noise_mag = 0.5;
	public static double _dist_thres = 100; // 360
	public static int _maximum_allowed_skipped_frames = 2;
	public static int _max_trace_length = 10;
	public static int _max_sec_before_stale = 2; // 2 sec for better computer but my mac sucks
	public static int _min_dist_change = 10;
}