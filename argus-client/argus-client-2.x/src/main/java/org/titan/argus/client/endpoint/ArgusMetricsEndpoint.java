package org.titan.argus.client.endpoint;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;

/**
 * @author starboyate
 *
 */
@Endpoint(id = "jvm")
public class ArgusMetricsEndpoint {

    @Autowired
    private MeterRegistry registry;



}
