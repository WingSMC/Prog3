import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class Format {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
}

/*
 * Lambdákkal és HashMappel szebb lenne
 */

public class Commands {
	public File wd = new File(System.getProperty("user.dir"));
	public final Scanner s = new Scanner(System.in);

	public void launch() {
		while (true) {
			try {
				System.out.print(Format.ANSI_GREEN + wd.getCanonicalPath() + Format.ANSI_RESET + " > ");
				final String cmd[] = s.nextLine().split(" ");
				switch (cmd[0]) {
					case "cd":
						wd = cd(cmd);
						break;
					case "ls":
						ls(cmd);
						break;
					case "pwd":
						pwd();
						break;
					case "mv":
						mv(cmd);
						break;
					case "cat":
						cat(cmd);
						break;
					case "wc":
						wc(cmd);
						break;
					case "exit":
						exit();
					default:
						if(cmd[0].length() > 0)
							System.out.println(colStr("\"" + cmd[0] + "\" is not recognized.", Format.ANSI_RED));
				}
				continue;
			} catch(final FileNotFoundException e) {
				System.out.println(colStr("File not found.", Format.ANSI_RED));
			} catch (final Exception e) {
				e.printStackTrace();
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
			if (cmd.length > 1) {
				switch (cmd[1]) {
					case "-l":
						entryString += (entry.isFile() ? " f " : " d ") + entry.length();
						break;
					default:
						System.out.println(colStr("Unknown argument \"" + cmd[1] + "\"", Format.ANSI_RED));
						return;
				}
			}
			System.out.println(entryString);
		}
	}

	File cd(String cmd[]) throws IOException {
		if (cmd.length > 1)
			return new File(wd, cmd[1]);
			System.out.println(colStr("Invalid argument list.", Format.ANSI_RED));
		return wd;
	}

	void mv(String cmd[]) throws IOException {
		if (cmd.length < 3) {
			System.out.println(colStr("Invalid argument list.", Format.ANSI_RED));
			return;
		}
		if (new File(wd, cmd[1]).renameTo(new File(wd, cmd[2])))
			System.out.println("Moved " + colStr(cmd[1], Format.ANSI_YELLOW) + " to " + colStr(cmd[2], Format.ANSI_YELLOW));
		else
			System.out.println(colStr("Couldn't rename file ", Format.ANSI_RED) + colStr(cmd[1], Format.ANSI_YELLOW));
	}

	void cat(String cmd[]) throws IOException {
		if(cmd.length < 2) {
			System.out.println(colStr("Invalid argument list.", Format.ANSI_RED));
			return;
		}
		BufferedReader br = new BufferedReader(new FileReader(new File(wd, cmd[1])));
		String line;
		while((line = br.readLine()) != null) {
			System.out.println(line);
		}
		br.close();
	}

	enum ReaderState {
		WORD,
		SPACE
	}

	void wc(String cmd[]) throws IOException {
		if(cmd.length < 2) {
			System.out.println(colStr("Invalid argument list.", Format.ANSI_RED));
			return;
		}
		FileReader fr = new FileReader(new File(wd, cmd[1]));
		int ch;
		int lineCnt = 1;
		int wordCnt = 0;
		int charCnt = 0;
		ReaderState state = ReaderState.SPACE;
		while((ch = fr.read()) != -1) {
			++charCnt;
			if(ch == '\n') {
				++lineCnt;
				state = ReaderState.SPACE;
				continue;
			}
			switch (state) {
				case WORD:
					if(ch == ' ') {
						state = ReaderState.SPACE;
					}
					break;
				case SPACE:
					if(ch != ' ' && ch != '\r') {
						++wordCnt;
						state = ReaderState.WORD;
					}
			}
		}
		System.out.println(
			colStr(cmd[1], Format.ANSI_YELLOW) +
			" stats:\n Character count: " + charCnt +
			"\n Word count: " + wordCnt +
			"\n Line count: " + lineCnt
		);
		fr.close();
	}

	void exit() {
		s.close();
		System.exit(0);
	}

	String colStr(String str, String color) {
		return color + str + Format.ANSI_RESET;
	}
}