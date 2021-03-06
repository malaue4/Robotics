package roboi;
import java.util.Random;

import behavior.*;
import lejos.hardware.Sound;
import lejos.hardware.device.NXTCam;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


/**
 * @author Grp10
 *
 */
public class Main {

	public static RoboPilot pilot;
	private static Arbitrator arbi;
	public static final Random RANDOM = new Random();
	//public static RoboCam camera;
	public static double approachDistance = 50;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Configuring Pilot");
		setupPilot();
		System.out.println("Configuring Camera");
		try(RoboCam camera = new RoboCam(SensorPort.S4)){
			System.out.println("Starting IR");
			try(EV3IRSensor infraRed = new EV3IRSensor(SensorPort.S1)){
				SampleProvider IRdistance = infraRed.getMode("Distance");
				System.out.println("Configuring Arbitrator");
				Behavior[] behaviorList = 
					{
							new Wander(pilot), 
							new MoveToTarget(pilot, Motor.B, camera, IRdistance),
							new Escape(IRdistance), 
							new Reject(camera, IRdistance, Motor.B, pilot), 
							new Attack(pilot, IRdistance, camera)
					};
				arbi = new Arbitrator(behaviorList);
				arbi.go();
				System.out.println("Ready!");
				Sound.beepSequenceUp();
			}
		}
	}

	private static void setupPilot() {
		double diameterL = 43.2;
		double diameterR = 43.2;
		Wheel wheelL = WheeledChassis.modelWheel(Motor.C, diameterL).offset(-77);
		Wheel wheelR = WheeledChassis.modelWheel(Motor.D, diameterR).offset(77);

		Chassis chassis = new WheeledChassis(new Wheel[] { wheelL, wheelR }, WheeledChassis.TYPE_DIFFERENTIAL);

		pilot = new RoboPilot(chassis);
	}

}
