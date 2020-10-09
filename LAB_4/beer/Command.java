package beer;

import java.util.List;
import java.util.Queue;

@FunctionalInterface
public interface Command {
	void run(List<Beer> beers, Queue<String> args);
}
