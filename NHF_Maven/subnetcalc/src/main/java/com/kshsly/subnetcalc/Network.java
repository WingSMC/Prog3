package com.kshsly.subnetcalc;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;

public class Network extends DefaultMutableTreeNode {
	private static final long serialVersionUID = -4061082007606228170L;

	private short[] octets;
	public short[] getOctets() { return octets; }
	private int mask;
	public int getMask() { return mask; }

	// negat�v �rt�kek nem igaz�n �rdekelnek
	public Network(short[] octets, int integer) throws Exception {
		this.setAddress(octets, integer);
	}

	public Network(Network superNet, long size) throws Exception {
		if(size < 2)
			throw new InvalidIPv4AddressException("The size should be at least 2...");
		int neededHostBits = (int) Math.ceil(Math.log(size + 2) / Math.log(2.0));
		this.mask = 32 - neededHostBits;
		long bitmask = makeMask(this.mask);
		long prevAddr = getLongFromOctets(superNet.nextFreeSubnetAddress());
		long nextSuitableAddress = (bitmask & prevAddr) + (1L << neededHostBits);
		
		this.octets = getOctetsFromLong(nextSuitableAddress);
				
		if(
			superNet.getNAddrs() - 2 < size ||
			getLongFromOctets(superNet.nextFreeNetworkAddress()) < nextSuitableAddress + getNAddrs()
		) throw new InvalidIPv4AddressException("The supernet doesn't have enough addresses to contain the new one. Make sure it isn't the 0.0.0.0");
	}
	
	// Number of hosts + 2 (net + broadcast)
	public long getNAddrs() {
		return (1L << (32 - mask));
	}
	
	// Next free network address after this network
	public short[] nextFreeNetworkAddress() {
		return getOctetsFromLong(getLongFromOctets(octets) + getNAddrs());
	}
	
	// Next free network address after the last subnet's address
	public short[] nextFreeSubnetAddress() throws Exception {
		try {
			Network lastChild = (Network) this.children.get(this.children.size() - 1);
			return lastChild.nextFreeNetworkAddress();
		} catch(Exception e) {
			return this.getOctets();
		}
	}

	public void setAddress(short[] octets, int mask) throws InvalidIPv4AddressException {
		if(octets.length != 4)
			throw new InvalidIPv4AddressException("This shouldn't happen.");
		else if(mask < 0 || 30 < mask)
			throw new InvalidIPv4AddressException("The provided mask (" + mask + ") is invalid.");
		for (int i = 0; i < 4; i++) {
			if(!isValidNetwork(octets, mask) || octets[i] < 0 || 255 < octets[i])
				throw new InvalidIPv4AddressException("The address " + octets[0] + "." + octets[1] + "." + octets[2] + "." + octets[3] + "/" + mask + " is not valid.");
		}
		this.octets = octets;
		this.mask = mask;
		this.children = new Vector<>();
	}
	
	public void add(Network net) {
		this.children.add(net);
	}

	@Override
	public String toString() {
		String ret = "";
		for(int i = 0; i < 3; ++i) {
			ret += octets[i] + ".";
		}
		return ret + octets[3] + "/" + mask;
	}

	
	
	// Static
	private static long getLongFromOctets(short octets[]) {
		long acc = octets[0];
		for(byte i = 1; i < 4; ++i) {
			acc *= 256;
			acc += octets[i];
		}
		return acc;
	}
	
	private static short[] getOctetsFromLong(long value) {
		short[] octets = new short[4];
		for(byte i = 3; i > -1; --i) {
			octets[i] = (short) (value % 256);
			value -= octets[i];
			value /= 256;
		}
		return octets;
	}
	
	private static long makeMask(int mask) {
		long rMask = 0xFFFFFFFF00000000L;
		rMask >>>= mask;
		return rMask;
	}

	private static long makeWildcard(int mask) {
		return makeMask(mask) ^ 0xFFFFFFFFFFFFFFFFL;
	}

	private static boolean isValidNetwork(short octets[], int mask) {
		return (makeWildcard(mask) & getLongFromOctets(octets)) == 0L;
	}
	
}
