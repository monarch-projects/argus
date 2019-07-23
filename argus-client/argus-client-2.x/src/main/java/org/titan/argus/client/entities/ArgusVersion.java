package org.titan.argus.client.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
@Builder
public class ArgusVersion {

	private String springBootVersion;

	private String springCloudVersion;

	@Value("${info.app.version}")
	private String projectVersion;

}
