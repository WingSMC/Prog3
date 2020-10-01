import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

@FunctionalInterface
interface Command {
	File execute(File wd, String[] args) throws IOException;
}

@FunctionalInterface
interface Argument {
	CustomList<String> execute(File wd, String[] args) throws IOException;
}

public class Commands {
	public static File wd = new File(System.getProperty("user.dir"));
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";

	public static void main(final String[] args) {
		final Scanner s = new Scanner(System.in);
		final Map<String, Command> commands = new HashMap<String, Command>();

		commands.put("pwd", (wd, cmd) -> {
			System.out.println(wd.getCanonicalPath());
			return wd;
		});

		commands.put("ls", (wd, cmd) -> {
			File entries[] = wd.listFiles();
			for (File entry : entries) {
				String entryString = ANSI_YELLOW + entry.getName() + ANSI_RESET;
				if(cmd.length > 1) {
					if(cmd[1].equals("-l"))
						entryString += (entry.isFile() ? " f " : " d ") + entry.getTotalSpace();
					else
						System.out.println(ANSI_RED + "Unknown flag " + cmd[1] + ANSI_RESET);
				}
				System.out.println(entryString);
			}
			return wd;
		});

		commands.put("exit", (wd, cmd) -> {
			s.close();
			System.exit(0);
			return null;
		});

		while (true) {
			try {
				System.out.print(ANSI_GREEN + wd.getCanonicalPath() + ANSI_RESET + " > ");
				final String cmd[] = s.nextLine().split("\s");
				final Command command = commands.get(cmd[0]);
				if(command == null) {
					System.out.println(ANSI_RED + "\"" + cmd[0] + "\" is not recognized." + ANSI_RESET);
					continue;
				}
				wd = command.execute(wd, cmd);
			} catch (final Exception e) {
				System.out.println();
				continue;
			}
		}
	}
}