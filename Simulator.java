/**
 * 
 */
package simul;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * 
 */
public class Simulator extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;
	private Timer timer;
	private TimerTask task;
	private BufferStrategy strategy;
	private final int WIDTH = 600, HEIGHT = 800;
	private ArrayList<Particle> particles = new ArrayList<>();

	public Simulator() {
		setTitle("Simulator(￣^￣)ゞ");
		createWindow();
		addComponents();
		setVisible(true);
		// set up double buffering
		createBufferStrategy(3);
		setIgnoreRepaint(true);
		strategy = getBufferStrategy();

		// start simulator -- safe zone
		timer = new Timer();
		task = new SimulatorTask();
		timer.schedule(task, 100L, 17L);
	}

	private class Operations implements Consumer<Particle> {
		private Graphics2D gc;

		public Operations(Graphics2D gc) {
			this.gc = gc;
		}

		@Override
		public void accept(Particle p) {
			p.render(gc);
			p.update();
			checkOutBounds(p);
			synchronized (particles) {
				for (Particle p1: particles) {
					if (p.equals(p1)) {
						continue;
					}/**if (collision)
					p.flipVectors();
					**/
				}
			}

		}

	}

	private class SimulatorTask extends TimerTask {
		@Override
		public void run() {
			do {
				do {
					Graphics2D gc = (Graphics2D) getGraphics();
					// clear screen
					gc.setClip(0, 0, getWidth(), getHeight());
//			gc.setBackground(getBackground());
					gc.clearRect(0, 0, getWidth(), getHeight());

					synchronized (particles) {
						particles.stream().forEach(new Operations(gc));
					}
					gc.dispose();
				} while (strategy.contentsRestored());
			} while (strategy.contentsLost());
			getToolkit().sync();
		} // run ends here

	}// Task ends here

	private void checkOutBounds(Particle p) {
		int x = (int) p.getxLocat();
		int y = (int) p.getyLocat();

		double xv = Math.abs(p.getxVelo());
		double yv = Math.abs(p.getyVelo());

		if (x < 0) {
			p.setxVelo((xv - 0.1 * xv));
		} else if (x > WIDTH) {
			p.setxVelo(-(xv - 0.1 * xv));
		}
		if (y < 0) {
			p.setyVelo((yv - 0.1 * yv));
		} else if (y > HEIGHT) {
			p.setyVelo(-(yv - 0.1 * yv));
		}

	}

	private void addComponents() {
		// TODO Auto-generated method stub

	}

	private void createWindow() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMouseListener(this);
		setLocationRelativeTo(null);
		pack();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Particle p = new Particle(x, y);
		p.setyAcc(1);
		p.setxVelo(1);
		synchronized (particles) {
			particles.add(p);
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Simulator());

	}

}
