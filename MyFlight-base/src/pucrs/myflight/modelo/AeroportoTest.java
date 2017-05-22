package pucrs.myflight.modelo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AeroportoTest {	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAeroporto() {
		Geo pos = new Geo(-30, -51);
		Aeroporto a1 = new Aeroporto("001", "Salgado Filho", pos);
		assertEquals(a1.getLocal(), pos);		
	}

	@Test
	public void testGetLocal() {
		Geo pos = new Geo(-30, -51);
		Aeroporto a1 = new Aeroporto("001", "Salgado Filho", pos);
		assertNotNull(a1.getLocal());
	}

}
