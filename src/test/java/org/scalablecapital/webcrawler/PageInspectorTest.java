package org.scalablecapital.webcrawler;

import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;

public class PageInspectorTest {

	@Test
    public void getJavascriptsTest() {
		String request="https://www.w3schools.com";
		Set<String> result=PageInspector.getJavascripts(request,100000);
		assertNotNull(result);
    }
}