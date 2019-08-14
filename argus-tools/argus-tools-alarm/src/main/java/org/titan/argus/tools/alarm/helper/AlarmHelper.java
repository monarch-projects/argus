package org.titan.argus.tools.alarm.helper;

import lombok.Builder;
import lombok.Data;
import org.titan.argus.tools.alarm.entities.Alarm;

/**
 * @author starboyate
 */
@Builder
@Data
public class AlarmHelper {
	private Alarm alarm;

	private SenderHelper senderHelper;
}
