package org.titan.argus.tools.alarm.core;

import org.titan.argus.model.entities.Alarm;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author starboyate
 */
public class AlarmScheduler {
	private static ScheduledExecutorService ALARM_SCHEDULER;

	public static void init(int corePoolSize) {
		ALARM_SCHEDULER = Executors.newScheduledThreadPool(corePoolSize,(r -> {
			Thread thread = new Thread(r);
			thread.setName("Alarm-Scheduler");
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
		Set<Alarm> allAlarm = AlarmHolder.getAllAlarm();
		allAlarm.forEach(alarm -> ALARM_SCHEDULER.scheduleAtFixedRate(new ArgusAlarmTask(alarm), 0, alarm.getDelayTime(), TimeUnit.MILLISECONDS));
	}
}
