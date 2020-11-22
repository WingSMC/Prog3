import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

public class SaxTask {
	final static Map<String, String> arguments = new HashMap<>();
	public static void main(String... args) {
		SaxTask.parseArgs(args);
		DefaultHandler bsf = new BusStopFilter(
			Double.parseDouble(arguments.get("lat")),
			Double.parseDouble(arguments.get("lon"))
		);
		try {
			SAXParser p = SAXParserFactory
				.newInstance()
				.newSAXParser();
			p.parse(new File(arguments.get("i")), bsf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		((BusStopFilter)bsf).sort();
		System.out.println(bsf);
	}

	/**
	 * Erősen feltételezve, hogy stimmelnek az argumentumok
	*/
	public static void parseArgs(String[] args) {
		String k = "";
		for (int i = 0; i < args.length; i++) {
			final String c = args[i];
			if (c.charAt(0) == '-') {
				k = c.substring(1);
			} else {
				arguments.put(k, c);
			}
		}
/* 		arguments.put("i", "bme.xml");
		arguments.put("lat", "47.4786346");
		arguments.put("lon", "19.0555773"); */
	}
}
