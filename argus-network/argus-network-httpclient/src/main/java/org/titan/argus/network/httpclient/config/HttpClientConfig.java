package org.titan.argus.network.httpclient.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 */
@PropertySource("classpath:/network/httpclient.properties")
@Configuration
public class HttpClientConfig {

	@Value("${http.pool.maxTotal}")
	public Integer maxTotal;

	@Value("${http.pool.defaultMaxPerRoute}")
	public Integer defaultMaxPerRoute;

	@Value("${http.pool.connectTimeout}")
	public Integer connectTimeout;

	@Value("${http.pool.connectionRequestTimeout}")
	public Integer connectionRequestTimeout;

	@Value("${http.pool.socketTimeout}")
	public Integer socketTimeout;

	@Value("${http.pool.validateAfterInactivity}")
	public Integer validateAfterInactivity;


	@Bean
	public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(){
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(maxTotal);
		connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		connectionManager.setValidateAfterInactivity(validateAfterInactivity);
		return connectionManager;
	}

	@Bean
	public HttpClientBuilder httpClientBuilder(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager){
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);

		return httpClientBuilder;
	}

	@Bean
	public CloseableHttpClient closeableHttpClient(HttpClientBuilder httpClientBuilder){
		return httpClientBuilder.build();
	}


	@Bean
	public RequestConfig.Builder builder(){
		RequestConfig.Builder builder = RequestConfig.custom();
		return builder.setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(connectionRequestTimeout)
				.setSocketTimeout(socketTimeout);
	}

	@Bean
	public RequestConfig requestConfig(RequestConfig.Builder builder){
		return builder.build();
	}


}
