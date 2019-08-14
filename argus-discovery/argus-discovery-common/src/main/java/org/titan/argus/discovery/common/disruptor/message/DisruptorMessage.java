package org.titan.argus.discovery.common.disruptor.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.titan.argus.discovery.common.disruptor.event.InstanceEvent;

/**
 * @author starboyate
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisruptorMessage {
	private InstanceEvent event;
}
