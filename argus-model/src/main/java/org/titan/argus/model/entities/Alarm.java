package org.titan.argus.model.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author starboyate
 */
@Data
@TableName("alarm")
public class Alarm implements Serializable {
	@TableId
	private Integer id;

	@TableField(value = "alarm_type")
	private String alarmType;

	@TableField(value = "event_type")
	private String eventType;

	@TableField(value = "from")
	private String from;

	@TableField(value = "to")
	private String to;

	@TableField(value = "account")
	private String account;

	@TableField(value = "key")
	private String key;

	@TableField(value = "subject")
	private String subject;

	@TableField(value = "body")
	private String body;

	@TableField(value = "host")
	private String host;

	@TableField(value = "create_time")
	private Long createTime;

	@TableField(value = "update_time")
	private Long updateTime;

	@TableField(value = "is_deleted")
	private Boolean isDeleted;
}
