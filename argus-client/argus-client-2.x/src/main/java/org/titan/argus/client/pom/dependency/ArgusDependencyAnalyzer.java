package org.titan.argus.client.pom.dependency;

import fr.dutra.tools.maven.deptree.core.InputType;
import fr.dutra.tools.maven.deptree.core.Node;
import fr.dutra.tools.maven.deptree.core.Parser;

import java.io.*;

/**
 * @author starboyate
 */
public class ArgusDependencyAnalyzer {
	public static Node analysis() {
		Node node = null;
		InputStream stream = ArgusDependencyAnalyzer.class.getClassLoader()
				.getResourceAsStream("dependency.txt");
		if (stream == null) {
			throw new IllegalArgumentException("can not load dependency.txt, please determine if there is a resources directory");
		}
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream));
			InputType type = InputType.TEXT;
			Parser parser = type.newParser();
			node = parser.parse(reader);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return node;
	}

}
