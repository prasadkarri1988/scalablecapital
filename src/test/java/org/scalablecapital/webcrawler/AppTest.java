package org.scalablecapital.webcrawler;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;
import org.junit.Test;

/**
 * Unit test for simple App.
 */

public class AppTest {

	/**
	 * testAppwithValidInput Test 
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void testAppwithValidInput() throws ExecutionException, InterruptedException {
		String[] args = { "javascript" };
		App.main(args);
		assertTrue(true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAppwithOutValidInput() throws ExecutionException, InterruptedException {
		String[] args = null;
		App.main(args);
	}
}
