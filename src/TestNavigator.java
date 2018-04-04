
import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.*;
public class TestNavigator{
	public static void main(String[] args){


		Wheel wheelL = WheeledChassis.modelWheel(Motor.C, 43.2).offset(-77);
		Wheel wheelR = WheeledChassis.modelWheel(Motor.D, 43.2).offset(77);

		Chassis chassis = new WheeledChassis(new Wheel[] { wheelL, wheelR }, WheeledChassis.TYPE_DIFFERENTIAL);

		MovePilot pilot = new MovePilot(chassis); 

		pilot.setAngularSpeed(450);// degree per sec
		pilot.setLinearSpeed(400);  // mm per sec

		Navigator robot = new Navigator(pilot);

		robot.goTo(-420,0);
		robot.goTo(-420,-297);
		robot.goTo(0,-297);
		robot.goTo(0,0);
		while(robot.isMoving()) Sound.pause(500);
		robot.rotateTo(0);
	}
}