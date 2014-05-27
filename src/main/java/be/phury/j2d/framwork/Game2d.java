package be.phury.j2d.framwork;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;

public class Game2d extends Canvas {

	// add some system property control flags. Setting these flags as "True"
	// with a captial will notify you via the console if the particular
	// pipeline D3D or Opengl has been successfully enabled. Using "true" all
	// lowercase will turn it on silently, you can still find out if it was
	// successful using the trace property as it will show calls to either D3D
	// or opengl, or neither.
	//
	// NOTE: trace does not seem to work for opengl with jdk1.6.0_20
	static {
		System.setProperty("sun.java2d.trace", "timestamp,log,count");
		System.setProperty("sun.java2d.transaccel", "True");
		System.setProperty("sun.java2d.opengl", "True");
		// System.setProperty("sun.java2d.d3d", "false"); //default on windows
		// System.setProperty("sun.java2d.ddforcevram", "true");
	}

	// app title.
	private static final String	TITLE	= "GPSnippets: BufferStrategy Snippet";

	// the strategy used for double buffering, or any number of buffered frames.
	private BufferStrategy strategy;

	// our time keeper
	private final Timer	timer;

	// the main render and update task.
	private TimerTask	renderTask;
	
	private Scene2d game;

	/**
	 * This configures the canvas component for rendering. Creates any objects
	 * we need and sets up some default listeners for component events.
	 */
	private Game2d(final Scene2d game) {
		this.game = game;
		// we will be doing our own rendering, using the strategy.
		this.setIgnoreRepaint(true);

		timer = new Timer(); // used for the render thread

		// add quick and dirty 2-directional movement for our block
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				game.keyPressed(e.getKeyCode());
			}

			@Override
			public void keyReleased(KeyEvent e) {
				game.keyReleased(e.getKeyCode());
			}
		});
	}

	/**
	 * Our drawing function which utilizes the BufferStrategy that enables us to
	 * do offscreen rendering without having to wait for swing to repaint, the
	 * component. It also eliminates flickering and splicing issues.
	 */
	private void render() {
		Graphics2D g2d = (Graphics2D) strategy.getDrawGraphics();
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(Color.WHITE);
		game.render(g2d);
		g2d.dispose();
		strategy.show();
		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * It is necessary to wait until after the component has been displayed in
	 * order to create and retrieve the buffer strategy. This is the part that
	 * took me the longest to figure out. But it makes sense since the component
	 * requires native resources in order to perform hardware acceleration and
	 * those resources are handled by the component itself, and are only
	 * available once the component is created and displayed.
	 */
	private void setup() {
		game.setup();
		this.createBufferStrategy(2);
		strategy = this.getBufferStrategy();
		start();
	}

	/**
	 * Initialize the render and update tasks, to call the render method, do
	 * timing and FPS counting, handling input and canceling existing tasks.
	 */
	private void start() {
		// if the render task is already running stop it, this may cause an
		// exception to be thrown if the task is already canceled.
		if (renderTask != null) {
			renderTask.cancel();
		}

		// our main task for handling the rendering and for updating and
		// handling input and movement events. The timer class isn't the most
		// reliable for game updating and rendering but it will suffice for the
		// purpose of this snippet.
		renderTask = new TimerTask() {
			long	lasttime	= System.currentTimeMillis();

			@Override
			public void run() {

				// get the current system time
				long time = System.currentTimeMillis();

				// calculate the time passed in milliseconds
				double dt = (time - lasttime) * 0.001;

				// save the current time
				lasttime = time;
				
				game.udpate(dt);

				render();
			}
		};

		// These will cap our frame rate but give us unexpected results if our
		// rendering or updates take longer than the 'period' time. It
		// is likely that we could have overlapping calls.
		timer.schedule(renderTask, 0, 16);
	}

	/**
	 * Stops the rendering cycle so that the application can close gracefully.
	 */
	private void stop() {
		renderTask.cancel();
	}

	/**
	 * Creates a Frame and adds a new canvas to the Frame, displays it and
	 * initializes the rendering method.
	 */
	public static void createAndDisplay(Scene2d scene2d, GameConfig config) {
		// Never mix swing and awt, since we use a canvas to utilize the
		// buffered strategy we will put the canvas in a Frame instead of a
		// JFrame.
		final Game2d canvas = new Game2d(scene2d);
		canvas.setSize(config.screenWidth(), config.screenHeight());
		final Frame frame = new Frame(TITLE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas);

		// convenience exiting from the demo using the ESCAPE key.
		canvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					canvas.stop(); // first stop the drawing and updating
					frame.setVisible(false); // hide the window quickly
					frame.dispose(); // release all system resources
					System.exit(0); // finally exit.
				}
			}
		});

		// need this to trap when the user attempts to close the window using
		// the close icon for the window, or the close option from the window
		// menu or alt+f4 or by other means.
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				canvas.stop(); // first stop the drawing and updating
				frame.setVisible(false); // hide the window quickly
				frame.dispose(); // release all system resources
				System.exit(0); // finally exit.
			}
		});
		frame.setSize(config.screenWidth()+18, config.screenHeight()+40);
		frame.setLocationRelativeTo(null); // centers window on screen
		frame.setVisible(true); // creates and displays the actual window

		// this is our scene setup to initialize all necessary configurable
		// objects and properties. Using a setup method helps control the way
		// things look from a single location, it can be extended to include
		// how things act as well.
		canvas.setup();
	}
}
