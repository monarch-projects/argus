package org.titan.argus.tools.alarm.core;

import com.google.common.base.Objects;
import org.titan.argus.model.entities.Alarm;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
public class AlarmScheduler {
	private static ScheduledExecutorService ALARM_SCHEDULER;

	private static final AtomicInteger COUNT = new AtomicInteger(0);

	public static void init(int corePoolSize) {
		ALARM_SCHEDULER = Executors.newScheduledThreadPool(corePoolSize,(r -> {
			Thread thread = new Thread(r);
			thread.setName("alarm-scheduler-thread-" + COUNT.getAndIncrement());
			if (thread.isDaemon()) {
				thread.setDaemon(false);
			}
			return thread;
		}));
	}

	public static void init() {
		init(Runtime.getRuntime().availableProcessors() * 2);
	}

	private static void execute() {
		Set<Alarm> allAlarm = AlarmHolder.getAllAlarm()
				.stream()
				.filter(alarm ->
						!Objects.equal(alarm.getEventType(), AlarmEventTypeConstant.REGISTER) &&
						!Objects.equal(alarm.getEventType(), AlarmEventTypeConstant.OFFLINE) &&
						!Objects.equal(alarm.getEventType(), AlarmEventTypeConstant.UNKONOWN))
				.collect(Collectors.toSet());
		allAlarm.forEach(alarm -> ALARM_SCHEDULER.scheduleAtFixedRate(new ArgusAlarmTask(alarm), 0, alarm.getDelayTime(), TimeUnit.MILLISECONDS));
	}
}
