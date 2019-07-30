package org.titan.argus.client.pom.dependency;

import fr.dutra.tools.maven.deptree.core.InputType;
import fr.dutra.tools.maven.deptree.core.Node;
import fr.dutra.tools.maven.deptree.core.Parser;
import org.titan.argus.client.constant.ArgusClientConstant;

import java.io.*;

/**
 * @author starboyate
 */
public class ArgusDependencyAnalyer {
	public static Node analysis() {
		Node node = null;
		try (FileInputStream fis = new FileInputStream(new File(ArgusClientConstant.DEPENDENCY_FILE_PATH))) {
			Reader reader = new BufferedReader(new InputStreamReader(fis));
			InputType type = InputType.TEXT;
			Parser parser = type.newParser();
			node = parser.parse(reader);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return node;
	}

}
