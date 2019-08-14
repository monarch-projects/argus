package org.titan.argus.tools.alarm.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author starboyate
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SenderHelper {
	private JavaMailSender emailSender;

	public static SenderHelper.SenderHelperBuilder builder() {
		return new SenderHelper.SenderHelperBuilder();
	}

	public static class SenderHelperBuilder {
		private JavaMailSender sender;

		SenderHelperBuilder() {
		}

		public SenderHelper.SenderHelperBuilder mail(final JavaMailSender sender) {
			this.sender = sender;
			return this;
		}
		public SenderHelper build() {
			return new SenderHelper(this.sender);
		}
	}
}
