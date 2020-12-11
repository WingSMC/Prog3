package com.kshsly.subnetcalc;

public class InvalidIPv4AddressException extends Exception {
	private static final long serialVersionUID = 5943257883395388404L;
	public InvalidIPv4AddressException(String msg) {
		super(msg);
	}
}
