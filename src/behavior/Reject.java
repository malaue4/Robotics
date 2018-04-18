/**
 * 
 */
package behavior;

import lejos.hardware.device.NXTCam;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.geometry.Rectangle2D;
import lejos.robotics.subsumption.Behavior;
import roboi.Main;
import roboi.RoboPilot;

/**
 * @author 
 *
 */
public class Reject implements Behavior {

	private NXTCam camera;
	private SampleProvider IRdistance;
	private double obj_minimum_size = 6;
	private RoboPilot pilot;
	private BaseRegulatedMotor neck;

	/**
	 * 
	 */
	public Reject(NXTCam camera, SampleProvider IRdistance, BaseRegulatedMotor neck, RoboPilot pilot) {
		this.camera = camera;
		this.IRdistance = IRdistance;
		this.neck = neck;
		this.pilot = pilot;
		
	}

	@Override
	public boolean takeControl() {
		int numberOfObjects = camera.getNumberOfObjects();
		float[] sample = new float[IRdistance.sampleSize()];
		IRdistance.fetchSample(sample , 0);
		if(numberOfObjects > 0 && sample[0] <= Main.approachDistance) {
			int oColor = camera.getObjectColor(0);
			if(oColor != 0) {
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
		// Shake head
		neck.rotateTo(-45);
		neck.rotateTo( 45);
		neck.rotateTo( 0);
		// Back off
		pilot.travel(-50);
		pilot.rotate(180);
	}

	@Override
	public void suppress() {
		neck.rotateTo(0, true);
		pilot.stop();
	}

}
