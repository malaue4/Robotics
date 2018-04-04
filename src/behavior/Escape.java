package behavior;

import lejos.hardware.Sound;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import roboi.Main;

public class Escape implements Behavior {
	SampleProvider IRdistance;
	public Escape(SampleProvider IRdistance) {
		this.IRdistance = IRdistance;
	}

	@Override
	public boolean takeControl() {
		float [] sample = new float[IRdistance.sampleSize()];
		IRdistance.fetchSample(sample, 0);
		if(sample[0] < 20) {
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		System.out.println("Escape");
		Sound.buzz();
		Main.pilot.travel(-80);
		Main.pilot.rotate(Main.RANDOM.nextInt(120)+120);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
