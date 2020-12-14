package com.kshsly.subnetcalc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;


class NetworkStaticTest {
	@ParameterizedTest
	@CsvSource({
		"192,168,0,0,3232235520",
		"172,16,0,1,2886729729",
		"0,0,0,0,0",
		"255,255,255,255,4294967295"
	})
	void testGetLongFromOctets(short o1, short o2, short o3, short o4, long expected) {
		assertEquals(expected, Network.getLongFromOctets(new short[] {o1, o2, o3, o4}));
	}

	@ParameterizedTest
	@CsvSource({
		"192,168,0,0,3232235520",
		"172,16,0,1,2886729729",
		"0,0,0,0,0",
		"255,255,255,255,4294967295"
	})
	void testGetOctetsFromLong(short e1, short e2, short e3, short e4, long input) {
		assertArrayEquals(new short[] {e1,e2,e3,e4}, Network.getOctetsFromLong(input));
	}

	@ParameterizedTest
	@CsvSource({"24,-256", "0,-4294967296", "32, -1"})
	void testMakeMask(int mask, long expected) {
		assertEquals(expected, Network.makeMask(mask));
	}
}
