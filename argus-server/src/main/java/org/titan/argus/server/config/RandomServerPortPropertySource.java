package org.titan.argus.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

/**
 * @author starboyate
 * 自定义随机端口生成解析类
 */
@Slf4j
public class RandomServerPortPropertySource extends PropertySource<RandomServerPort> {

    public static final String RANDOM_SERVER_PORT_PROPERTY_SOURCE_NAME = "randomServerPort";

    private static final String PREFIX = "randomServerPort.";

    public RandomServerPortPropertySource() {
        this(RANDOM_SERVER_PORT_PROPERTY_SOURCE_NAME);
    }

    public RandomServerPortPropertySource(String name) {
        super(name, new RandomServerPort());
    }

    public RandomServerPortPropertySource(String name, RandomServerPort source) {
        super(name, source);
    }

    @Override
    public Object getProperty(String name) {
        if (!name.startsWith(PREFIX)) {
            return null;
        }
        if (log.isTraceEnabled()) {
            log.trace("Generating randomServerPort property for {}", name);
        }
        return getRandomServerPortValue(name.substring(PREFIX.length()));
    }

    public Object getRandomServerPortValue(String type) {
        String range = getRange(type, "value");
        if (range != null) {
            return getNextValueInRange(range);
        }
        return null;
    }

    private String getRange(String type, String prefix) {
        if (type.startsWith(prefix)) {
            int startIndex = prefix.length() + 1;
            if (type.length() > startIndex) {
                return type.substring(startIndex, type.length() - 1);
            }
        }
        return null;
    }

    private int getNextValueInRange(String range) {
        String[] tokens = StringUtils.commaDelimitedListToStringArray(range);
        int start = Integer.parseInt(tokens[0]);
        if (tokens.length == 1) {
            return getSource().nextValue(start);
        }
        return getSource().nextValue(start, Integer.parseInt(tokens[1]));
    }
}
