import java.util.ArrayList;

public class CustomList<T> extends ArrayList<T> {
	private static final long serialVersionUID = 1L;

	public String join() {
		return join(" ");
	}

	public String join(final String joint) {
		int last = this.size() - 1;
		String product = "";
		for(int i = 0; i < last; i++) product += this.get(i) + joint;
		product += this.get(last);
		return product;
	}
}
