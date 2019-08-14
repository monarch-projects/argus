package org.titan.argus.tools.alarm.entities;

import lombok.*;

/**
 * @author starboyate
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailAlarm extends Alarm {
	private String host;

	private String subject;

	private Object body;

}
