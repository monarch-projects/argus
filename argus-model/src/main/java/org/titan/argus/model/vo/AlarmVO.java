package org.titan.argus.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.titan.argus.model.entities.Alarm;

import java.util.Date;

/**
 * @author starboytae
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmVO {
	private Integer id;

	private String alarmType;

	private String eventType;

	private String to;

	private String account;

	private String key;

	private String host;

	private Long delayTime;

	private Long createTime;
	public Alarm convert() {
		long time = System.currentTimeMillis();
		return Alarm.builder()
				.account(this.account)
				.alarmType(this.alarmType)
				.to(this.to)
				.host(this.host)
				.key(this.key)
				.delayTime(this.delayTime)
				.createTime(time)
				.updateTime(time)
				.build();
	}
}
