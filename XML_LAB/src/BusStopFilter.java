import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class BusStopFilter
extends DefaultHandler {
	Location point;
	boolean inNode = false;
	boolean valid = false;
	List<BusStop> busStops = new ArrayList<>();
	BusStop current;

	public BusStopFilter(double lat, double lon) {
		this.point = new Location(lat, lon);
	}

	@Override
	public void startElement(
		String uri,
		String localName,
		String qName,
		Attributes attributes
	) throws SAXException	{
		if(!inNode) {
			if(qName.equals("node")) {
				inNode = true;
				current = new BusStop(
					Double.parseDouble(attributes.getValue("lat")),
					Double.parseDouble(attributes.getValue("lon"))
				);
			}
		} else {
			String tagKey = attributes.getValue("k");
			String tagValue = attributes.getValue("v");
			if(tagKey == null) {
				System.out.println(qName);
			}
			if(tagKey.equals("highway")) {
				if(tagValue.equals("bus_stop")) {
					valid = true;
					return;
				}
				inNode = false;
			} else if(tagKey.equals("name")) {
				current.name = tagValue;
			} else if(tagKey.equals("old_name")) {
				current.oldName = tagValue;
			} else if(tagKey.equals("wheelchair")) {
				current.wheelchair = tagValue;
			}
		}
	}

	@Override
	public void endElement(
		String uri,
		String localName,
		String qName
	) throws SAXException	{
		if(qName.equals("node")) {
			if(valid) {
				current.dist = current.dist(point);
				busStops.add(current);
				valid = false;
			}
			inNode = false;
		}
	}

	public void sort() {
		Collections.sort(busStops);
	}

	@Override
	public String toString() {
		return busStops
		.stream()
		.map(BusStop::toString)
		.collect(Collectors.joining("\n"));
	}
}