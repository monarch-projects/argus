package org.titan.argus.client.test;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.shared.invoker.*;
import org.springframework.util.ResourceUtils;
import org.titan.argus.client.annotation.TestTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Properties;

/**
 * @author starboyate
 */
@TestTool
public class TestArgusDependencyHelper {


	public static void generateDependencyFile() {
		String dependencyPath = TestArgusPomPath.getClassResourcePath() + "src/main/resources" + File.separator + "dependency.txt";
		File file = new File(dependencyPath);
		if (!file.exists()) {
			InvocationRequest request = new DefaultInvocationRequest();
			Properties properties = new Properties();
			properties.setProperty("outputFile", file.getPath());
			properties.setProperty("outputAbsoluteArtifactFilename", "true");
			properties.setProperty("includeScope", "runtime");
			request.setPomFile(TestArgusPomPath.getPomFile());
			request.setGoals(Collections.singletonList("dependency:tree"));
			request.setProperties(properties);
			invoke(request);
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

	public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
		URL resource = TestArgusDependencyHelper.class.getClassLoader().getResource("");
		String urlString = resource.toString();
		urlString = urlString.substring(0, urlString.indexOf("class"));
		System.out.println(urlString);
		File pomFile = null;
		try {
			File file = new File(ResourceUtils.getURL("resources").getPath());
			File parent = new File(new File(file.getParent()).getParent());
			if (parent.isDirectory()) {
				String pom = parent.getPath() + "/pom.xml";
				pomFile = new File(pom);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}




}