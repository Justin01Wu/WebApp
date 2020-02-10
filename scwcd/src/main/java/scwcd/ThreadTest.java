package scwcd;

public class ThreadTest implements Runnable {

    public void run() {
        	boolean continueFlag = true;
            while(continueFlag)  {
                try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					continueFlag= false;
				}
            }
        
    }
    public static void main(String[] args)
    {
        Runnable r = new ThreadTest();

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
    }
}
