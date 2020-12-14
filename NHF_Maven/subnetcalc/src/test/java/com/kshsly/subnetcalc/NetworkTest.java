package com.kshsly.subnetcalc;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

public class NetworkTest {
	@ParameterizedTest
	@CsvSource({
		"192,168,0,0,24,192,168,1,0",
		"100,0,0,0,30,100,0,0,4",
		"100,0,0,4,30,100,0,0,8"
	})
	void testNextFreeNetworkAddress(
		short o1,
		short o2,
		short o3,
		short o4,
		int mask,
		short e1,
		short e2,
		short e3,
		short e4
		) throws Exception {
		assertEquals(
			new Network(new short[]{o1,o2,o3,o4}, mask)
				.nextFreeNetworkAddress(),
			Network.getLongFromOctets(new short[]{e1,e2,e3,e4})
		);
	}
}
