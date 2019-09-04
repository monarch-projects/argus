package org.titan.argus.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author starboyate
 */
public class NetUtil {

	public static boolean isConnect(String ip, Integer port) {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(ip, port));
			return true;
		} catch (IOException e) {

		}
		return false;
	}
}
