public class Producer extends Thread {
	private String s;
	private boolean running;
	private ConcurrentQueue fifo;
	private int sleepTime;

	public Producer(ConcurrentQueue f, String s, int n) {
		this.s = s;
		this.fifo = f;
		this.sleepTime = n;
	}

	@Override
	public void run() {
		this.running = true;
		for(int i = 0; running;++i) {
			try {

				synchronized(this.fifo) {
					fifo.put(this.s + " " + i);
				}

				System.out.println("produced " + this.s + " " + i + " " + System.currentTimeMillis());
				Thread.sleep(this.sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void signal() {
		this.running = false;
	}
}
