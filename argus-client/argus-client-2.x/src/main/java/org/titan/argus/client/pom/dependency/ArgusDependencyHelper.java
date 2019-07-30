package org.titan.argus.client.pom.dependency;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.shared.invoker.*;
import org.titan.argus.client.constant.ArgusClientConstant;
import org.titan.argus.client.pom.ArgusPomPath;

import java.io.File;
import java.util.Collections;
import java.util.Properties;

/**
 * @author starboyate
 */
public class ArgusDependencyHelper {


	public static void generateDependencyFile() {
		File file = new File(ArgusClientConstant.DEPENDENCY_FILE_PATH);
		if (!file.exists()) {
			InvocationRequest request = new DefaultInvocationRequest();
			Properties properties = new Properties();
			properties.setProperty("outputFile", ArgusClientConstant.DEPENDENCY_FILE_PATH);
			properties.setProperty("outputAbsoluteArtifactFilename", "true");
			properties.setProperty("includeScope", "runtime");
			request.setPomFile(ArgusPomPath.getPomPath());
			request.setGoals(Collections.singletonList("dependency:tree"));
			request.setProperties(properties);
			invoke(request);
		}
	}

	public static void removeFile() {
		File file = new File(ArgusClientConstant.DEPENDENCY_FILE_PATH);
		if (file.exists()) {
			file.delete();
		}
	}



	private static void invoke(InvocationRequest request) {
		Invoker invoker = new DefaultInvoker();
		String mavenHome = getMavenHome();
		if (StringUtils.isBlank(mavenHome)) {
			throw new IllegalArgumentException("can not find MAVEN_HOME, Please set env variables");
		}
		invoker.setMavenHome(new File(mavenHome));
		try {
			InvocationResult result = invoker.execute(request);
			if (result.getExitCode() != 0) {
				throw new Exception("generate dependency file error");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static String getMavenHome() {
		String mavenHome = System.getenv("MAVEN_HOME");
		if (!StringUtils.isBlank(mavenHome)) {
			int binIndex = mavenHome.indexOf(System.lineSeparator() + "bin");
			if (binIndex > 0) {
				return mavenHome.substring(0, binIndex);
			}
		}
		return mavenHome;
	}



}
