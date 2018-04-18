/**
 * 
 */
package behavior;

import lejos.hardware.device.NXTCam;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.geometry.Rectangle2D;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import roboi.Main;
import roboi.RoboPilot;

/**
 * @author 
 *
 */
public class Attack implements Behavior {

	private NXTCam camera;
	private RoboPilot pilot;
	private SampleProvider IRdistance;
	private double obj_minimum_size = 6;
	
	/**
	 * 
	 */
	public Attack(RoboPilot pilot, SampleProvider IRdistance, NXTCam camera) {
		this.pilot = pilot;
		this.IRdistance = IRdistance;
		this.camera = camera;
	}
	
	@Override
	public boolean takeControl() {
		int numberOfObjects = camera.getNumberOfObjects();
		float[] sample = new float[IRdistance.sampleSize()];
		IRdistance.fetchSample(sample , 0);
		if(numberOfObjects > 0 && sample[0] <= Main.approachDistance) {
			int oColor = camera.getObjectColor(0);
			if(oColor == 0) {
				Rectangle2D rect = camera.getRectangle(0);
				if(Math.min(rect.getWidth(), rect.getHeight()) > obj_minimum_size) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void action() {
		System.out.println("ATTACK!!!");
		pilot.attack();
		double distance = Main.approachDistance;
		float[] sample = new float[IRdistance.sampleSize()];
		while(distance > 5 && distance <= Main.approachDistance) {
			IRdistance.fetchSample(sample, 0);
			distance = sample[0];
		}
		pilot.stop();
		System.out.println("attack complete");
	}
	
	@Override
	public void suppress() {
		System.out.println("attack suppressed");
		pilot.stop();
	}

}
