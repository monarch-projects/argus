package org.titan.argus.client.test;



import java.io.File;
import java.net.URL;

/**
 * @author starboyate
 */
public class TestArgusPomPath {
	private static File PROXY_SERVICE_POM_File;

	public static File getPomFile() {
		if (PROXY_SERVICE_POM_File == null) {
			PROXY_SERVICE_POM_File = findPomFile();
		}
		return PROXY_SERVICE_POM_File;
	}

	public static String getClassResourcePath() {
		URL resource = TestArgusPomPath.class.getClassLoader().getResource("");
		System.err.println(resource);
		String urlString = resource.toString();
		int prefixLength = "file:".length();
		return urlString.substring(prefixLength, urlString.indexOf("target"));
	}

	private static File findPomFile() {
		File pomFile = null;
		try {
			File file = new File(TestArgusPomPath.getClassResourcePath());
			String pom = file.getPath() + "/pom.xml";
			pomFile = new File(pom);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pomFile;
	}

}
