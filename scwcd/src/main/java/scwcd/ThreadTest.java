package scwcd;

public class ThreadTest implements Runnable {

	public void run() {
		doJob();
	}

	private void doJob() {
		doRealJob();
	}
	private void doRealJob() {
		boolean continueFlag = true;
		while (continueFlag) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				continueFlag = false;
			}
		}
	}

}
