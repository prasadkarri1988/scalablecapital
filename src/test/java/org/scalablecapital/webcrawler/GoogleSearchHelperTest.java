package org.scalablecapital.webcrawler;

import static org.junit.Assert.assertNotNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.junit.Test;

public class GoogleSearchHelperTest {

	@Test
	public void searchGoogleBasedonStringTest() {
		String query="javascript";
		int num=200;
		int timeout = 50000;
		CompletableFuture<Set<String>> searchResult=GoogleSearchHelper.searchGoogle(query,num,timeout);
		assertNotNull(searchResult);
	}
	
}
