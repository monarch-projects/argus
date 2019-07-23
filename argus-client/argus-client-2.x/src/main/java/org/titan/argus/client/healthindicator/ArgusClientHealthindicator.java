package org.titan.argus.client.healthindicator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

/**
 * @author starboyate
 *  自定义健康检查端点
 */
public class ArgusClientHealthindicator extends AbstractHealthIndicator {


	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {

	}
}
