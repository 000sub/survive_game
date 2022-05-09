package edu.sku.hw;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.SwingWorker;

public class TimerTask {
	private AtomicBoolean isStarted =  new AtomicBoolean(false);
	private AtomicBoolean isRunning  = new AtomicBoolean(false);
	private AtomicBoolean isDone     = new AtomicBoolean(false);
	
	private int lengthOfTask;
	private int current = 0;	

	public TimerTask() {
		lengthOfTask = Constant.timer_max;
	}

	public void go() {
		isRunning.set(true);
		
		if (!isStarted.get()) {
			isDone.set(false);
			isStarted.set(true);
			current = Constant.timer_max;
			
			final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				
				@Override
				protected Void doInBackground() throws Exception {
					while (!isDone.get()) {
						if (isRunning.get()) {
							try {
								Thread.sleep(1000); 				// sleep for a second
								current--;							// make some progress
								
								if (current <= 0) {
									onDown();
									current = lengthOfTask;
								}
								
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

	public int getLengthOfTask() {
		return lengthOfTask;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent() {
		this.current = Constant.timer_max;;
	}

	public void onDown() {
		isDone.set(true);
		isStarted.set(false);
		isRunning.set(false);
	}

	public boolean isDone() {
		return isDone.get();
	}
}
