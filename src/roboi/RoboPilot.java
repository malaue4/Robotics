package roboi;

import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class RoboPilot extends MovePilot {

	private double normalAcceleration = 200;
	private double turnAcceleration = 200;
	private double attackSpeed = getMaxLinearSpeed(); 
	private double normalSpeed = 200;
	private double turnSpeed = 450;
			
	public RoboPilot(Chassis chassis) {
		super(chassis);

		setAngularSpeed(turnSpeed); 	// degrees per sec
		setLinearSpeed(normalSpeed); 	// mm per sec
		setAngularAcceleration(turnAcceleration);
		setLinearAcceleration(normalAcceleration);
	}

	/**
	 * Moves forward at top speed. Returns immediately.
	 */
	public void attack() {
		setLinearAcceleration(attackSpeed);
		setLinearSpeed(attackSpeed);
		forward();
	}
	
	public void stop() {
		super.stop();
		setLinearAcceleration(normalAcceleration);
		setLinearSpeed(normalSpeed);
	}
}
