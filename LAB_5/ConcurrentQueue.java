import java.util.LinkedList;

public class ConcurrentQueue extends LinkedList<String> {
	private static final long serialVersionUID = -1864852393368867178L;

	public synchronized String get() throws InterruptedException {
		while(super.isEmpty())
			this.wait();
		this.notify();
		return super.poll();
	}

	public synchronized void put(String s) throws InterruptedException {
		while(super.size() > 9)
			this.wait();
		super.add(s);
		this.notify();
	}
}
