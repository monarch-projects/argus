package org.titan.argus.client.pom;


import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author starboyate
 */
public class ArgusPomAnalyzer {

	public static Model analysisPom(){
		Model model = null;
		try {
			MavenXpp3Reader reader = new MavenXpp3Reader();
			model = reader.read(new InputStreamReader(getPomInputStream()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return model;
	}


	private static InputStream getPomInputStream() {
		URL resource = ArgusPomAnalyzer.class.getClassLoader().getResource("");
		InputStream is = null;
		if (resource != null) {
			String urlString = resource.toString();
			boolean isInJar = urlString.indexOf("!") > -1;
			int prefixLength;
			if (isInJar) {
				prefixLength = "jar:file:".length();
			} else {
				prefixLength = "file:".length();
			}
			urlString = urlString.substring(prefixLength);
			String[] strings = urlString.split("/");
			StringBuilder sb = new StringBuilder();
			for (String s : strings) {
				sb.append(s);
				sb.append(File.separator);
				if (isInJar && s.contains(".jar")) {
					break;
				} else {
					if (s.contains("target")) {
						break;
					}
				}
			}
			String path = sb.toString();
			if (isInJar) {
				path = path.substring(0, path.lastIndexOf("!"));
				is = analysisJar(new File(path));
			} else {
				File arachive = new File(path);
				for (File f : arachive.listFiles()) {
					if (f.getPath().contains(".jar")) {
						is = analysisJar(f);
						break;
					}
				}
			}
		}
		return is;

	}

	private static InputStream analysisJar(File file) {
		if (!file.exists()) {
			throw new IllegalArgumentException("file is no exists");
		}
		try {
			JarFile jarFile = new JarFile(file);
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry jarEntry = entries.nextElement();
				if (jarEntry.getName().endsWith("pom.xml")) {
					return ArgusPomAnalyzer.class.getClassLoader().getResourceAsStream(jarEntry.getName());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		throw new IllegalArgumentException("can not find pom.xml");
	}
}
