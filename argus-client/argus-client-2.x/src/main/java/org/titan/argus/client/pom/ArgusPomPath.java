package org.titan.argus.client.pom;

import org.springframework.util.ResourceUtils;

import java.io.File;

/**
 * @author starboyate
 */
public class ArgusPomPath {
	private static File PROXY_SERVICE_POM_PATH;

	public static File getPomPath() {
		if (PROXY_SERVICE_POM_PATH == null) {
			PROXY_SERVICE_POM_PATH = findPomPath();
		}
		return PROXY_SERVICE_POM_PATH;
	}

	private static File findPomPath() {
		File pomFile = null;
		try {
			File file = new File(ResourceUtils.getURL("classpath").getPath());
			File parent = new File(new File(file.getParent()).getParent());
			if (parent.isDirectory()) {
				String pom = parent.getPath() + "/pom.xml";
				pomFile = new File(pom);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pomFile;
	}

}
