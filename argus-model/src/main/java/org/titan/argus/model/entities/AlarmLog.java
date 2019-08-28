package org.titan.argus.model.entities;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("alarm")
public class AlarmLog {
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

	@TableField(value = "subject")
	private String subject;

	@TableField(value = "body")
	private String body;

	@TableField(value = "create_time")
	private Long createTime;

	@TableField(value = "update_time")
	private Long updateTime;

	@TableField(value = "is_deleted")
	private Boolean isDeleted;
}
