package roboi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lejos.hardware.device.NXTCam;
import lejos.hardware.port.Port;
import lejos.robotics.geometry.Rectangle2D;

public class RoboCam extends NXTCam {
	private int timeToLive = 50;
	private long lastUpdate = 0;
	private List<Rectangle2D> rectList = Collections.synchronizedList(new ArrayList<Rectangle2D>());
	private List<Integer> colorList = Collections.synchronizedList(new ArrayList<Integer>());
	public RoboCam(Port port) {
		super(port);
		sendCommand('A'); // sort by size
		sendCommand('E'); // start tracking
	}

	private synchronized void updateList(){
		if(lastUpdate+timeToLive < System.currentTimeMillis()) {
			rectList.clear();
			colorList.clear();
			for (int i = 0; i < super.getNumberOfObjects(); i++) {
				rectList.add(super.getRectangle(i));
				colorList.add(super.getObjectColor(i));
			}
			lastUpdate = System.currentTimeMillis();
		}
	}
	
	@Override
	public int getNumberOfObjects() {
		updateList();
		return rectList.size();
	}

	@Override
	public Rectangle2D getRectangle(int i) {
		return getRectangle(i, false);
	}
	public Rectangle2D getRectangle(int i, boolean updateList) {
		if(updateList) updateList();
		if(rectList.size()==0) return new Rectangle2D.Double();
		return rectList.get(i);
	}

	@Override
	public int getObjectColor(int i) {
		return getObjectColor(i, false);
	}
	public int getObjectColor(int i, boolean updateList) {
		if(updateList) updateList();
		if(colorList.size()==0) return -1;
		return colorList.get(i);
	}
}
