public class Application {
	public static void main(String[] args) {
		ConcurrentQueue fifo = new ConcurrentQueue();
		Producer p = new Producer(fifo, "pro", 100);
		Consumer c = new Consumer(fifo, "hai", 1000);
		p.start();
		c.start();
	}
}
