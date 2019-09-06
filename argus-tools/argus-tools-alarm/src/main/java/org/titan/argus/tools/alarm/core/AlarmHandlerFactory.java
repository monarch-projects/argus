//package org.titan.argus.tools.alarm.core;
//
//import org.titan.argus.model.entities.Alarm;
//
///**
// * @author starboyate
// */
//public class AlarmHandlerFactory {
//
//	public static void handler(Alarm alarm) {
//		String eventType = alarm.getEventType();
//		AlarmHandler handler = null;
//		switch (eventType) {
//			case AlarmEventTypeConstant.REDIS:
//				new RedisAlarmHandler().handler(alarm);
//				break;
//			case AlarmEventTypeConstant.MONGODB:
//				new MongodbAlarmHandler().handler(alarm);
//				break;
//		}
//	}
//}
