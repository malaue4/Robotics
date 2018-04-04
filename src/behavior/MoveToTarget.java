package behavior;

import lejos.hardware.Sound;
import lejos.hardware.device.NXTCam;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.robotics.geometry.Rectangle2D;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class MoveToTarget implements Behavior {
	private MovePilot pilot;
	private BaseRegulatedMotor neck;
	private NXTCam camera;
	int obj_minimum_size = 10;
	int viewAngle = 30;
	int viewWidth = 176/2;
	int numberOfObjects;
	
	public MoveToTarget(MovePilot pilot, BaseRegulatedMotor neck, NXTCam camera) {
		this.pilot = pilot;
		this.neck = neck;
		this.camera = camera;
		
		camera.sendCommand('A'); // sort by size
		camera.sendCommand('E'); // start tracking
	}

	@Override
	public boolean takeControl() {
		numberOfObjects = camera.getNumberOfObjects();
		if(numberOfObjects > 0) {
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
		neck.rotateTo(0, true);
		pilot.rotate(-lookDirection);
		neck.setSpeed(_s);
		pilot.setAngularSpeed(_a);
		pilot.forward();
		while(numberOfObjects > 0) {
			numberOfObjects = camera.getNumberOfObjects();
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
