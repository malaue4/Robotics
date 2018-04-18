package behavior;

import lejos.hardware.Sound;
import lejos.hardware.device.NXTCam;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.robotics.SampleProvider;
import lejos.robotics.geometry.Rectangle2D;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import roboi.Main;

public class MoveToTarget implements Behavior {
	private MovePilot pilot;
	private BaseRegulatedMotor neck;
	private NXTCam camera;
	private SampleProvider IRdistance;
	int obj_minimum_size = 10;
	int viewAngle = 30;
	int viewWidth = 176/2;
	int numberOfObjects;
	
	public MoveToTarget(MovePilot pilot, BaseRegulatedMotor neck, NXTCam camera, SampleProvider IRdistance) {
		this.pilot = pilot;
		this.neck = neck;
		this.camera = camera;
		this.IRdistance = IRdistance;
	}

	@Override
	public boolean takeControl() {
		numberOfObjects = camera.getNumberOfObjects();
		float[] sample = new float[IRdistance.sampleSize()];
		IRdistance.fetchSample(sample , 0);
		if(numberOfObjects > 0 && sample[0] > Main.approachDistance) {
			Rectangle2D rect = camera.getRectangle(0);
			if(Math.min(rect.getWidth(), rect.getHeight()) > obj_minimum_size) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void action() {
		System.out.println("MoveToTarget");
		Rectangle2D rect = camera.getRectangle(0);
		int lookDirection = (int) (viewAngle*(rect.getCenterX()-viewWidth)/viewWidth);
		System.out.println(lookDirection);
		int _s = neck.getSpeed();
		int _a = (int)pilot.getAngularSpeed();
		neck.setSpeed(100);
		pilot.setAngularSpeed(100);
		neck.rotateTo(lookDirection);
		pilot.rotate(-lookDirection, true);
		neck.rotateTo(0);
		neck.setSpeed(_s);
		pilot.setAngularSpeed(_a);
		pilot.forward();
		float[] sample = new float[IRdistance.sampleSize()];
		IRdistance.fetchSample(sample , 0);
		while(numberOfObjects > 0 && sample[0] > Main.approachDistance) {
			numberOfObjects = camera.getNumberOfObjects();
			IRdistance.fetchSample(sample , 0);
		}
		pilot.stop();
		System.out.println("Target Lost");
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		pilot.stop();
		neck.rotateTo(0);
	}

}
