package org.titan.argus.server.controller;

import ch.qos.logback.classic.LoggerContext;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.network.httpclient.util.ArgusHttpClient;
import org.titan.argus.server.core.ArgusActuatorConstant;
import org.titan.argus.server.core.InstanceMetadataHolder;
import org.titan.argus.server.response.ObjectDataResponse;
import org.titan.argus.service.InstanceService;
import org.titan.argus.service.exception.BusinessException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
@RestController
public abstract class BaseController {

	private Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	InstanceService instanceService;

	@Autowired
	ArgusHttpClient httpClient;

	@Autowired
	InstanceMetadataHolder holder;



	ObjectDataResponse proxyGet(String suffix, String id, Map<String, Object> requestParam) {
		ArgusInstance instance = this.instanceService.getInstanceById(id);
		String url = getUrl(instance.getHomePageUrl(), suffix);
		String entity;
		try {
			entity = httpClient.doGet(url, requestParam);
		} catch (Exception ex) {
			throw new BusinessException(ex.getMessage());
		}
		return new ObjectDataResponse<>(JSON.parseObject(entity, Object.class));
	}

	ObjectDataResponse proxyGet(String suffix, String id) {
		return this.proxyGet(suffix, id, null);
	}


	ObjectDataResponse proxyPost(String suffix, String id, Object requestBody) {
		ArgusInstance instance = this.instanceService.getInstanceById(id);
		String url = getUrl(instance.getHomePageUrl(), suffix);
		ArgusHttpClient.HttpResponse httpResponse;
		try {
			httpResponse = httpClient.doPost(url, requestBody);
		} catch (Exception ex) {
			throw new BusinessException(ex.getMessage());
		}
		 return new ObjectDataResponse<>(JSON.parseObject(httpResponse.getBody(), Object.class));
	}

	ObjectDataResponse proxyPut(String suffix, String id, Object requestBody) {
		ArgusInstance instance = this.instanceService.getInstanceById(id);
		String url = getUrl(instance.getHomePageUrl(), suffix);
		ArgusHttpClient.HttpResponse httpResponse;
		try {
			httpResponse = httpClient.doPut(url, requestBody);
		} catch (Exception ex) {
			throw new BusinessException(ex.getMessage());
		}

		return new ObjectDataResponse<>(JSON.parseObject(httpResponse.getBody(), Object.class));
	}

	ObjectDataResponse proxyDelete(String suffix, String id, Object requestBody) {
		ArgusInstance instance = this.instanceService.getInstanceById(id);
		String url = getUrl(instance.getHomePageUrl(), suffix);
		String entity;
		try {
			entity = httpClient.doDelete(url);
		} catch (Exception ex) {
			throw new BusinessException(ex.getMessage());
		}
		return new ObjectDataResponse<>(JSON.parseObject(entity, Object.class));
	}

	String getUrl(String prefix, String suffix) {
		return prefix + suffix;
	}

}
