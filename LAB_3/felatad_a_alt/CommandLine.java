import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class CommandLine {
	public File wd;
	public Scanner s;
	public Map<String, Command> commands = new HashMap<String, Command>();
	private boolean running = false;

	public CommandLine() {
		this(System.getProperty("user.dir"), null);
	}

	public CommandLine(String path) {
		this(path, null);
	}

	public CommandLine(Map<String, Command> commands) throws IOException {
		this(System.getProperty("user.dir"), commands);
	}

	public void run() {
		s = new Scanner(System.in);
		running = true;
		while (running) {
			try {
				System.out.print(Format.ANSI_GREEN + wd.getCanonicalPath() + Format.ANSI_RESET + " > ");
				final String cmd[] = s.nextLine().split("-");
				final Command command = commands.get(cmd[0].split(" ")[0]);
				if(command == null) {
					System.out.println(Format.ANSI_RED + "\"" + cmd[0] + "\" is not recognized." + Format.ANSI_RESET);
					continue;
				}
				wd = command.execute(wd, cmd);
			} catch (final Exception e) {
				System.out.println();
			}
		}
		s.close();
		s = null;
	}

	public void stop() {
		running = false;
	}


	public CommandLine(String path, Map<String, Command> commands) throws IOException {
		commands.put("pwd", (wd, cmd) -> {
			System.out.println(wd.getCanonicalPath());
			return wd;
		});

		commands.put("ls", (wd, cmd) -> {
			File entries[] = wd.listFiles();
			for (File entry : entries) {
				String entryString = Format.ANSI_YELLOW + entry.getName() + Format.ANSI_RESET;
				if(cmd.length > 1) {
					if(cmd[1].equals("-l"))
						entryString += (entry.isFile() ? " f " : " d ") + entry.getTotalSpace();
					else
						System.out.println(Format.ANSI_RED + "Unknown flag " + cmd[1] + Format.ANSI_RESET);
				}
				System.out.println(entryString);
			}
			return wd;
		});

		commands.put("exit", (wd, cmd) -> {
			stop();
			return null;
		});

		this.wd = new File(path);
		this.commands.putAll(commands);
	}
}