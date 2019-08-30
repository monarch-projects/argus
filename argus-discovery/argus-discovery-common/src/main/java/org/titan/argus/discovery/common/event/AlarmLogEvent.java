package org.titan.argus.discovery.common.event;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.titan.argus.model.entities.AlarmLog;

/**
 * @author starboyate
 */
@Getter
public class AlarmLogEvent extends ApplicationEvent {
	private AlarmLog log;
	/**
	 * Create a new ApplicationEvent.
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public AlarmLogEvent(Object source, AlarmLog log) {
		super(source);
		this.log = log;
	}
}
