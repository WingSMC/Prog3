public class BusStop
extends Location
implements Comparable<BusStop> {
	String name;
	String oldName;
	String wheelchair;
	double dist;

	public BusStop(double lat, double lon) {
		super(lat, lon);
	}

	@Override
	public String toString() {
		String ret = "Megálló:\n    Név: " + name;
		if(oldName != null)
			ret += " (" + oldName + ")";
		if(wheelchair != null)
			ret += "\n    Kerekesszék: " + wheelchair;
		ret += "\n    Dist: " + dist;
		return ret; 
	}

	@Override
	public int compareTo(BusStop b2) {
		return dist > b2.dist ? 1 : -1;
	}
}
