import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.device.NXTCam;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.geometry.Rectangle2D;

public class TestCam {
	final static int INTERVAL = 1000; // milli
	
	public static void main(String[] args) {
		NXTCam camera = new NXTCam(SensorPort.S4);
		
		System.out.println(camera.getProductID() + "\n" +camera.getVersion());
		
		camera.sendCommand('A'); // sort by size
		camera.sendCommand('E'); // start tracking
		
		int obj_minimum_size = 6;
		int numberOfObjects = 0;
		Sound.setVolume(20);
		BaseRegulatedMotor neck = Motor.B;
		int viewAngle = 30;
		int viewWidth = 176/2;
		
		while(!Button.ESCAPE.isDown()) {
			numberOfObjects = camera.getNumberOfObjects();
			System.out.println("Num objects: "+numberOfObjects);
			
			if(numberOfObjects > 0 && numberOfObjects < 9) {
				Rectangle2D rect = camera.getRectangle(0);
				if(Math.min(rect.getWidth(), rect.getHeight()) > obj_minimum_size) {
					int d = (int) rect.getWidth();
					Sound.playTone(2000-d*10,50);
					//System.out.println("Object was found with size: "+rect.getWidth());
					//System.out.printf("X: %.2f\n Y: %.2f\n W: %.2f\n H: %.2f",rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
					//System.out.println(camera.getObjectColor(0));
					int lookDirection = (int) (viewAngle*(rect.getCenterX()-viewWidth)/viewWidth);
					System.out.println(lookDirection);
					neck.rotateTo(lookDirection, true);
				}
			}
			Sound.pause(INTERVAL);
		}
		
		camera.close();
	}
}
