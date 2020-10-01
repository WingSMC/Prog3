@FunctionalInterface
interface Argument {
	CustomList<String> execute(File wd, String[] args) throws IOException;
}