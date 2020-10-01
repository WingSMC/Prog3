import java.util.Scanner;
import java.io.File;
import java.io.IOException;

class Format {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
}


public class Commands {
	public File wd = new File(System.getProperty("user.dir"));
	public final Scanner s = new Scanner(System.in);

	public void run() {
		while (true) {
			try {
				System.out.print(Format.ANSI_GREEN + wd.getCanonicalPath() + Format.ANSI_RESET + " > ");
				final String cmd[] = s.nextLine().split(" ");
				switch (cmd[0]) {
					case "pwd": pwd(); break;
					case "ls": ls(cmd); break;
					case "cd": wd = cd(cmd); break;
					case "exit": exit();
					default: System.out.println(Format.ANSI_RED + "\"" + cmd[0] + "\" is not recognized." + Format.ANSI_RESET);
				}
			} catch (final Exception e) {
				System.out.println();
			}
		}
	}

	void pwd() throws IOException {
		System.out.println(wd.getCanonicalPath());
	}

	void ls(String cmd[]) {
		File entries[] = wd.listFiles();
		for (File entry : entries) {
			String entryString = Format.ANSI_YELLOW + entry.getName() + Format.ANSI_RESET;
			if(cmd.length > 1) {
				switch (cmd[1]) {
					case "-l":
						entryString += (entry.isFile() ? " f " : " d ") + entry.length();
						break;
					default:
						System.out.println(Format.ANSI_RED + "Unknown argument " + cmd[1] + Format.ANSI_RESET);
						return;
				}
			}
			System.out.println(entryString);
		}
	}

	File cd(String cmd[]) throws IOException {
		if(cmd.length > 1) return new File(wd.getAbsolutePath() + "/" + cmd[1]);
		System.out.println(Format.ANSI_RED + "Not enough arguments." + Format.ANSI_RESET);
		return wd;
	}

	void exit() {
		s.close();
		System.exit(0);
	}
}