package org.titan.argus.tools.alarm.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


/**
 * @author starboyate
 */
@Getter
public class MiddleWareNodeEvent extends ApplicationEvent {

	private String host;

	private Integer port;



	public MiddleWareNodeEvent(Object source, String host, Integer port) {
		super(source);
		this.host = host;
		this.port = port;
	}
}
