public class Consumer extends Thread {
	private boolean running;
	private final String s;
	private ConcurrentQueue fifo;
	private int sleepTime;

	public Consumer(ConcurrentQueue f, String s, int n) {
		this.fifo = f;
		this.s = s;
		this.sleepTime = n;
	}

	@Override
	public void run() {
		this.running = true;
		while(running) {
			try {
				String read;
				synchronized(this.fifo) {
					read = fifo.get();
				}

				System.out.println("consumed " + this.s + " " + read + " " + System.currentTimeMillis());
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
