package application;

public class Vertex implements Comparable<Vertex> {

	private String country;
	private Integer distance = 0;

	public Vertex(String countryName) {
		this.country = countryName;
	}

	public Vertex(String countryName, int distance) {
		this.country = countryName;
		this.distance = distance;
	}

	public String getCountry() {
		return country;
	}

	public int getDistance() {
		return distance;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Vertex [Country = " + country + ", distance=" + distance + "]";
	}

	@Override
	public int compareTo(Vertex o) {
		if (this.distance < o.distance)
			return -1;
		else if (this.distance > o.distance)
			return 1;
		else
			return this.getCountry().compareToIgnoreCase(o.getCountry());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((distance == null) ? 0 : distance.hashCode());
		result = prime * result + ((country.equals(null) ) ? 0 : country.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (distance == null) {
			if (other.distance != null)
				return false;
		} else if (!distance.equals(other.distance))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		return true;
	}
}