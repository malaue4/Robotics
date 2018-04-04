package behavior;
import java.util.Random;

import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;
import roboi.Main;

public class Wander implements Behavior {

	public Wander() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		System.out.println("Wander");
		Main.pilot.travel(100+Main.RANDOM.nextInt(200));
		Main.pilot.rotate(100-Main.RANDOM.nextInt(200));
	}

	@Override
	public void suppress() {
		Main.pilot.stop();
		Sound.beep();
	}

}
