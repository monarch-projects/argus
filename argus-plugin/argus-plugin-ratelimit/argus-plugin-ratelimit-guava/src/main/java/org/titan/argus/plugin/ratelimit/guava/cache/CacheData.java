package org.titan.argus.plugin.ratelimit.guava.cache;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @Title: CacheData
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/24
 */
@Data
@AllArgsConstructor
public class CacheData {
    private int limit;

    private long waitTime;
}
