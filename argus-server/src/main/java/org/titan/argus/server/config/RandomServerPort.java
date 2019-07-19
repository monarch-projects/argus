package org.titan.argus.server.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author starboyate
 * 随机生成端口
 */
@Slf4j
public class RandomServerPort {

    private int serverPort;

    private final int DEFAULT_START = 0;

    private final int DEFAULT_END = 10000;

    private final String OS_NAME = System.getProperty("os.name");

    private final String OS_PREFIX = "Windows";

    public int nextValue(Integer start) {
        return nextValue(start, DEFAULT_END);
    }


    /**
     * 生成随机端口
     * @param start
     * @param end
     * @return
     */
    public int nextValue(int start, int end) {
        start = start < DEFAULT_START ? DEFAULT_START : start;
        end = end > DEFAULT_END ? DEFAULT_END : end;
        if (serverPort == 0) {
            synchronized (this) {
                if (serverPort == 0) {
                    serverPort = RandomUtils.nextInt(start, end);
                }
            }
        }
        return serverPort;
    }

}
