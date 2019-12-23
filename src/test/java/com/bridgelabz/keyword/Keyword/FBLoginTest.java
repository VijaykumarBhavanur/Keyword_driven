package com.bridgelabz.keyword.Keyword;

import java.io.IOException;

import org.testng.annotations.Test;

public class FBLoginTest {

	public KeywordEngine engine;

	@Test
	public void fbLogin() throws IOException {
		engine = new KeywordEngine();
		engine.startExecution("Sheet1"); //Sheet containing data
	}
}
