package org.titan.argus.tools.alarm.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.titan.argus.tools.alarm.enums.AlarmTypeEnum;


/**
 * @author starboyate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {


	/**
	 * alarm rule type,default email
	 */
	private String alarmType = AlarmTypeEnum.EMAIL.name();

	/**
	 * handler event type
	 */
	private String eventType;

	/**
	 * message from account
	 */
	private String from;

	/**
	 * message to account
	 */
	private String to;


	/**
	 * username
	 */
	private String account;

	/**
	 * key
	 */
	private String key;




}
