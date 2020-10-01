@FunctionalInterface
interface Command {
	File execute(File wd, String[] args) throws IOException;
}
