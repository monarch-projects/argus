package org.titan.argus.tools.alarm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.titan.argus.tools.alarm.listener.AlarmListener;

/**
 * @author starboyate
 */
@Configuration
public class AlarmAutoConfiguration {

	@Bean
	public AlarmListener alarmListener(AlarmHolder holder) {
		return new AlarmListener(holder);
	}

}
