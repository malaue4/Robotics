import lejos.hardware.*;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class TestIR{
	public static void main(String[] args){
		System.out.println("Started, press exit to stop");
		double diameterL = 43.2;
		double diameterR = 43.2;
		Wheel wheelL = WheeledChassis.modelWheel(Motor.C, diameterL).offset(-77);
		Wheel wheelR = WheeledChassis.modelWheel(Motor.D, diameterR).offset(77);

		Chassis chassis = new WheeledChassis(new Wheel[] { wheelL, wheelR }, WheeledChassis.TYPE_DIFFERENTIAL);

		MovePilot pilot = new MovePilot(chassis);
		try(EV3IRSensor infraRed = new EV3IRSensor(SensorPort.S1)){
			Sound.beepSequenceUp();
			SampleProvider IRdistance = infraRed.getMode("Distance");
			Sound.setVolume(20);
			float [] sample = new float[IRdistance.sampleSize()];
			
			int state = 0;
			int d = 20;
			while(!Button.ESCAPE.isDown()){
				IRdistance.fetchSample(sample,0);
				if(d != (int) sample[0]) System.out.println("dist "+d); 
				d = (int) sample[0];
				if(d<100) Sound.playTone(2000-d*20,50);
				else Sound.pause(50);
				if(!pilot.isMoving()) state = 0;
				if(d < 10 && state != -1) {
					pilot.travel(-(20-d), true);
					state = -1;
				} else if(d > 20 && state != 1) {
					pilot.travel(d-20, true);
					state = 1;
				}
				else if(state != 0 && (d > 10 && d < 20)){
					state = 0;
					pilot.stop();
				}
			}
			Sound.setVolume(10);
		}
		Sound.beepSequence();
	}
}
