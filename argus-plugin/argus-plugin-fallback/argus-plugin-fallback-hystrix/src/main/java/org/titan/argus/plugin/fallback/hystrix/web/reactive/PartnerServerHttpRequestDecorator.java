package org.titan.argus.plugin.fallback.hystrix.web.reactive;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.UnpooledByteBufAllocator;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import static reactor.core.scheduler.Schedulers.single;

public class PartnerServerHttpRequestDecorator extends ServerHttpRequestDecorator {
	private Logger logger = LoggerFactory.getLogger(PartnerServerHttpRequestDecorator.class);
	private final StringWriter cachedCopy = new StringWriter();
	private InputStream dataBuffer;
	private DataBuffer bodyDataBuffer;
	private int getBufferTime = 0;
	private byte[] bytes;

	public byte[] getBytes() {
		return this.bytes;
	}

	public PartnerServerHttpRequestDecorator(ServerHttpRequest delegate) {
		super(delegate);
	}
	@Override
	public Flux<DataBuffer> getBody() {
		if (getBufferTime == 0) {
			getBufferTime++;
			Flux<DataBuffer> flux = super.getBody();
			return flux
					.publishOn(single())
					.map(this::cache)
					.doOnComplete(() -> trace(getDelegate(), cachedCopy.toString()));


		} else {
			return Flux.just(getBodyMore());
		}

	}


	private DataBuffer getBodyMore() {
		NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
		DataBuffer dataBuffer = nettyDataBufferFactory.wrap(bytes);
		bodyDataBuffer = dataBuffer;
		return bodyDataBuffer;
	}

	private DataBuffer cache(DataBuffer buffer) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			dataBuffer = buffer.asInputStream();
			bytes = IOUtils.toByteArray(dataBuffer);
			NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
			DataBuffer dataBuffer = nettyDataBufferFactory.wrap(bytes);
			bodyDataBuffer = dataBuffer;
			return bodyDataBuffer;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void trace(ServerHttpRequest request, String requestBody) {
		logger.info(requestBody);
	}

}
