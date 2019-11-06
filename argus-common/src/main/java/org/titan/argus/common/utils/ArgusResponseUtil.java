package org.titan.argus.common.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author starboyate
 */
public class ArgusResponseUtil {
	public static void makeResponse(HttpServletResponse response, String contentType,
			int status, Object value) throws IOException {
		response.setContentType(contentType);
		response.setStatus(status);
		response.getOutputStream().write(JSONObject.toJSONString(value).getBytes());
	}
}
