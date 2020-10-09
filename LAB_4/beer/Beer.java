package beer;

import java.io.Serializable;

public record Beer (
	String name,
	String style,
	double strength
) implements Serializable {
	private static final long serialVersionUID = -9076249591610669351L;

	@Override
	public String toString() {
		return name+" "+style+" "+strength;
	}
}
