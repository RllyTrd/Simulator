/**
 * 
 */
package simul;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

/**
 * 
 */
public class Particle {
	private double xLocat, yLocat, xVelo, 
	yVelo, xAcc, yAcc;
	private final double RADIUS = 10;
	private long timeStamp;
	private Color[] palette = {Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.GRAY, Color.YELLOW};
	private Color color;

	public Particle(double x, double y) {
		xLocat = x;
		yLocat = y;
		setTimeStamp(System.currentTimeMillis());
	}
	public Particle(double x, double y, boolean colored) {
		this(x,y);
		Random rand = new Random();
		if (colored) {
			color = palette[rand.nextInt(palette.length)];
		} else {
			color = Color.RED;
		}
		
		setTimeStamp(System.currentTimeMillis());
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public void render(Graphics2D gc) {
		Color color = gc.getColor();
		gc.setColor(Color.RED);
		gc.fillOval((int)(xLocat-RADIUS), (int)(yLocat-RADIUS), 
				2*(int)RADIUS, 2*(int)RADIUS);
		gc.setColor(color);
	}
	public void update() {
		//Euler integration does not work well in simulators
		double deltaTime = System.currentTimeMillis() - timeStamp;
		xLocat += xVelo*deltaTime;
		yLocat += yVelo*deltaTime;
		
		//update Velocity
		xVelo += xAcc*deltaTime/100;
		yVelo += yAcc*deltaTime/100;
		timeStamp = System.currentTimeMillis();
	}
	public void flipVectors() {
		xVelo = -1 * xVelo;
		yVelo = -1 * yVelo;
	}

	public double getxLocat() {
		return xLocat;
	}

	public void setxLocat(double xLocat) {
		this.xLocat = xLocat;
	}

	public double getyLocat() {
		return yLocat;
	}

	public void setyLocat(double yLocat) {
		this.yLocat = yLocat;
	}

	public double getxVelo() {
		return xVelo;
	}

	public void setxVelo(double xVelo) {
		this.xVelo = xVelo;
	}

	public double getyVelo() {
		return yVelo;
	}

	public void setyVelo(double yVelo) {
		this.yVelo = yVelo;
	}

	public double getxAcc() {
		return xAcc;
	}

	public void setxAcc(double xAcc) {
		this.xAcc = xAcc;
	}

	public double getyAcc() {
		return yAcc;
	}

	public void setyAcc(double yAcc) {
		this.yAcc = yAcc;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Particle [x = "+ (int)xLocat + ", y = " + (int)yLocat;
	}
}
