package edu.sku.hw;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.SwingWorker;

/**
 * The Class DemoTask.
 *
 * @author ashraf
 */
public class DemoTask {
	private static final int TASK_LENGTH = 1000;
	private AtomicBoolean isStarted =  new AtomicBoolean(false);
	private AtomicBoolean isRunning = new AtomicBoolean(false);
	private AtomicBoolean isDone = new AtomicBoolean(false);
	private int lengthOfTask;
	private int current = 0;	
	private String statMessage;

	public DemoTask() {
		// Compute length of task...
		// In a real program, this would figure out
		// the number of bytes to read or whatever.
		lengthOfTask = TASK_LENGTH;
	}

	/**
	 * Called from SwingTimerDemo to start the task.
	 */
	public void go() {
		isRunning.set(true);
		if (!isStarted.get()) {
			isDone.set(false);
			isStarted.set(true);
			statMessage = null;
			current = 0;
			final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					// Fake a long task, making a random amount of progress every second.
					while (!isDone.get()) {
						if (isRunning.get()) {
							try {
								Thread.sleep(1000); // sleep for a second
								current += Math.random() * 100; // make some progress
								if (current >= lengthOfTask) {
									onDown();
									current = lengthOfTask;
								}
								statMessage = "Completed " + current + " out of " + lengthOfTask + ".";
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					return null;
				}
			};
			worker.execute();
		}
	}

	public void pause() {
		this.isRunning.set(false);
	}

	/**
	 * Called from SwingTimerDemo to find out how much work needs to be done.
	 */
	public int getLengthOfTask() {
		return lengthOfTask;
	}

	/**
	 * Called from SwingTimerDemo to find out how much has been done.
	 */
	public int getCurrent() {
		return current;
	}

	public void onDown() {
		isDone.set(true);
		isStarted.set(false);
		isRunning.set(false);
		statMessage = null;
	}

	/**
	 * Called from SwingTimerDemo to find out if the task has completed.
	 */
	public boolean isDone() {
		return isDone.get();
	}

	/**
	 * Returns the most recent status message, or null if there is no current
	 * status message.
	 */
	public String getMessage() {
		return statMessage;
	}

}
