package org.titan.argus.plugin.mongodb.util;

import java.text.DecimalFormat;

/**
 * @author starboyate
 */
public final class HexTransformationUtils {
	public static String formatSize(long size) {
		DecimalFormat df = new DecimalFormat("#.00");
		String sizeString = "";
		if (size < 1024) {
			sizeString = df.format((double) size) + "B";
		} else if (size < 1048576) {
			sizeString = df.format((double) size / 1024) + "K";
		} else if (size < 1073741824) {
			sizeString = df.format((double) size / 1048576) + "M";
		} else {
			sizeString = df.format((double) size / 1073741824) + "G";
		}
		return sizeString;
	}
}
