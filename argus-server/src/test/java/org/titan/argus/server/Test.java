package org.titan.argus.server;

import org.titan.argus.client.test.TestArgusDependencyHelper;

public class Test {
	@org.junit.Test
	public void test() {
		TestArgusDependencyHelper.generateDependencyFile();
	}
}
