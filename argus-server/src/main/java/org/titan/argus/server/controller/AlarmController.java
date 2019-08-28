package org.titan.argus.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.model.entities.Alarm;
import org.titan.argus.model.response.BaseResponse;
import org.titan.argus.model.vo.AlarmVO;
import org.titan.argus.service.AlarmService;
import org.titan.argus.tools.alarm.event.AlarmRuleUpdateEvent;

/**
 * @author starboyate
 */
@RestController
@RequestMapping("/alarm")
public class AlarmController {
	@Autowired
	private AlarmService alarmService;

	@Autowired
	private ApplicationEventPublisher publisher;
	@GetMapping
	public BaseResponse getAllAlarm() {
		return BaseResponse.success(this.alarmService.list());
	}

	@PostMapping
	public BaseResponse addAlarm(@RequestBody AlarmVO vo) {
		Alarm convert = vo.convert();
		if (this.alarmService.save(convert)) {
			Alarm alarm = this.alarmService.getById(convert.getId());
			this.publisher.publishEvent(new AlarmRuleUpdateEvent(this, alarm));
			return BaseResponse.success(alarm);
		}
		return BaseResponse.error(null);
	}

	@PutMapping
	public BaseResponse updateAlarm(@RequestBody AlarmVO vo) {
		Alarm convert = vo.convert();
		if (this.alarmService.updateById(convert)) {
			Alarm alarm = this.alarmService.getById(convert.getId());
			this.publisher.publishEvent(new AlarmRuleUpdateEvent(this, alarm));
			return BaseResponse.success(alarm);
		}
		return BaseResponse.error(null);
	}
}
