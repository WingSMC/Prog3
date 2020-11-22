public class Location {
	double lat, lon;
	public Location(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public double dist(Location l2) {
		final double R = 6371000;
		double phi1 = Math.toRadians(lat);
		double phi2 = Math.toRadians(l2.lat);
		double dphi = phi2 - phi1;
		double dl = Math.toRadians(l2.lon - lon);
		double a = Math.sin(dphi / 2) * Math.sin(dphi / 2) +
			Math.cos(phi1) * Math.cos(phi2) *
			Math.sin(dl / 2) * Math.sin(dl / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return R * c;
	}
}
