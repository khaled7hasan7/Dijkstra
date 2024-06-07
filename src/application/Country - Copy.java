package application;

import java.util.ArrayList;
import java.util.LinkedList;

public class Country implements Comparable<Country> {
	
	double lontude ;
	double latitude ;
	String name ;
	ArrayList<Country> adjacents ;
	
	 public Country() {
	super();
	}

	public Country(String line ) {
		String token [] = line.split(",");
		this.adjacents = new ArrayList<Country>();
		this.lontude = Double.parseDouble(token[2]);
		this.latitude = Double.parseDouble(token[1]);
		this.name = token[0];
	}

	@Override
	public String toString() {
		return name + "";
	}

	public Country(double lontude, double latitude, String name) {
		super();
		this.lontude = lontude;
		this.latitude = latitude;
		this.name = name;
	}

	@Override
	public int compareTo(Country c) {
		
		return this.name.compareTo(c.name);
	}

	public Country(String name , int dum) {
		
		this.name = name;
	}


	 
}
