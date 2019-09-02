package org.titan.argus.server.core;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.titan.argus.server.initializer.ArgusInitializer;

import java.util.List;

/**
 * @author starboyate
 */
public class ArgusInitializerProcessor {
	private static final Logger logger = LoggerFactory.getLogger(ArgusServerInitializer.class);


	public static void loadInitializer() {
		List<Class<ArgusInitializer>> allSubClass = Lists.newArrayList();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class<? extends ClassLoader> loaderClass = loader.getClass();
//		try {
//
//		}
	}

}
