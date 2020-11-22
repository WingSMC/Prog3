import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class TagCounter
extends DefaultHandler {
	private Map<String, Integer> elementCount = new HashMap<>();

	@Override
	public void startElement(
		String uri,
		String localName,
		String qName,
		Attributes attributes
	) throws SAXException	{
		Integer cnt = elementCount.get(qName);
		if(cnt == null)	cnt = 0;
		elementCount.put(qName, cnt + 1);
	}

	@Override
	public String toString() {
		return elementCount
			.entrySet()
			.stream()
			.map(e -> e.getKey() + ": " + e.getValue())
			.collect(Collectors.joining("\n"));
	}
}