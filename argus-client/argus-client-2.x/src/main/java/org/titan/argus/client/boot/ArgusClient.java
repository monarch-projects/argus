package org.titan.argus.client.boot;

import org.titan.argus.client.pom.dependency.ArgusDependencyHelper;

/**
 * @author starboyate
 */
public class ArgusClient {

	public static void initialize() {
		ArgusDependencyHelper.generateDependencyFile();

	}

	public static void shutdown() {
		ArgusDependencyHelper.removeFile();
	}
}
