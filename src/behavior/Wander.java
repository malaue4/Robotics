package behavior;

import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;
import roboi.Main;
import roboi.RoboPilot;

public class Wander implements Behavior {

	private RoboPilot pilot;

	public Wander(RoboPilot pilot) {
		this.pilot = pilot;
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		System.out.println("Wander");
		//pilot.arc((2-Main.RANDOM.nextInt(4))*400, Main.RANDOM.nextInt(15)+25);
		pilot.travel(100+Main.RANDOM.nextInt(200));
		pilot.rotate(-100+Main.RANDOM.nextInt(200));
	}

	@Override
	public void suppress() {
		Main.pilot.stop();
	}

}
