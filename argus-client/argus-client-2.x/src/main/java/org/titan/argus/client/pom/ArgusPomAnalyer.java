package org.titan.argus.client.pom;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;


import java.io.File;
import java.io.FileInputStream;

/**
 * @author starboyate
 */
public class ArgusPomAnalyer {

	private static Model POM_MODEL;

	public static Model analysis() {
		if (POM_MODEL == null) {
			POM_MODEL = analysis(ArgusPomPath.getPomPath());
		}
		return POM_MODEL;
	}

	private static Model analysis(File file) {
		Model model = null;
		try (FileInputStream fis = new FileInputStream(file)) {
			MavenXpp3Reader reader = new MavenXpp3Reader();
			model = reader.read(fis);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return model;
	}

}
