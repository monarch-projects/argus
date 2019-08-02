package fr.dutra.tools.maven.deptree.core;

/**
 * @author Alexandre Dutra
 *
 */
public enum InputType {

	TEXT {

		@Override
		public Parser newParser() {
			return new TextParser();
		}
	};

	public abstract Parser newParser();

}

