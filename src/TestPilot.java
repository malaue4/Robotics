import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.*;

public class TestPilot{
	public static void main(String[] args){

		double diameterL = 43.2;
		double diameterR = 43.2;
		Wheel wheelL = WheeledChassis.modelWheel(Motor.C, diameterL).offset(-77);
		Wheel wheelR = WheeledChassis.modelWheel(Motor.D, diameterR).offset(77);

		Chassis chassis = new WheeledChassis(new Wheel[] { wheelL, wheelR }, WheeledChassis.TYPE_DIFFERENTIAL);

		MovePilot pilot = new MovePilot(chassis);
		pilot.setAngularSpeed(450); 	// degrees per sec
		pilot.setLinearSpeed(200); 	// mm per sec
		pilot.setAngularAcceleration(200);
		pilot.setLinearAcceleration(200);
		
		pilot.arc(210, 180);
		pilot.rotate(90);
		
		pilot.travel(420);         	// mm
		pilot.rotate(90);        	// degree  
		pilot.travel(297);
		pilot.rotate(90);        	// degree
		pilot.travel(420);         	// mm
		pilot.rotate(90);        	// degree  
		pilot.travel(297);
		pilot.rotate(90);        	// degree
		
		pilot.rotate(-90);        	// degree  
		pilot.travel(-297);
		pilot.rotate(-90);        	// degree
		pilot.travel(-420);         	// mm
		pilot.rotate(-90);        	// degree  
		pilot.travel(-297);
		pilot.rotate(-90);        	// degree
		pilot.travel(-420);         	// mm

		pilot.rotate(-90);
		pilot.arc(210, -180);
		
		pilot.stop();      
	}
}
