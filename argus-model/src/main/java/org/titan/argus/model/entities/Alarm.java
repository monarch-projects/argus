package org.titan.argus.model.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.titan.argus.model.vo.AlarmVO;

import java.io.Serializable;

/**
 * @author starboyate
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("alarm")
public class Alarm implements Serializable {
	@TableId
	private Integer id;

	@TableField(value = "alarm_type")
	private String alarmType;

	@TableField(value = "event_type")
	private String eventType;

	@TableField(value = "`to`")
	private String to;

	@TableField(value = "account")
	private String account;

	@TableField(value = "`key`")
	private String key;

	@TableField(value = "`host`")
	private String host;

	@TableField(value = "delay_time")
	private Long delayTime;

	@TableField(value = "create_time")
	private Long createTime;

	@TableField(value = "update_time")
	private Long updateTime;

	@TableField(value = "is_deleted")
	private Boolean isDeleted;

	public AlarmLog convertToAlarmLog() {
		long time = System.currentTimeMillis();
		return AlarmLog.builder()
				.id(this.id)
				.account(this.account)
				.alarmType(this.alarmType)
				.eventType(this.eventType)
				.host(this.host)
				.key(this.key)
				.to(this.to)
				.delayTime(this.delayTime)
				.createTime(time)
				.updateTime(time)
				.build();
	}

	public AlarmVO convertToAlarmVO() {
		long time = System.currentTimeMillis();
		return AlarmVO.builder()
				.id(this.id)
				.account(this.account)
				.alarmType(this.alarmType)
				.eventType(this.eventType)
				.host(this.host)
				.key(this.key)
				.to(this.to)
				.delayTime(this.delayTime)
				.createTime(time)
				.build();
	}

}
