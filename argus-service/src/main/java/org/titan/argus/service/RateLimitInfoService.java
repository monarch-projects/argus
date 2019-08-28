package org.titan.argus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.titan.argus.model.dto.RateLimitConfigDTO;
import org.titan.argus.model.entities.RateLimitInfo;
import org.titan.argus.model.request.RateLimitClientDataRequest;

import java.util.Collection;

/**
 * @Title: RateLimitInfoService
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/27
 */
public interface RateLimitInfoService extends IService<RateLimitInfo> {

    /**
     * 处理客户端（rate limit plugin）发来的请求。未入库的入库，已经入库的忽略（按appname,clzname,methodname过滤）
     *
     * @param requests
     * @return
     */
    boolean processClientData(Collection<RateLimitClientDataRequest> requests);

    Collection<RateLimitConfigDTO> getConfigByAppName(String appName);

    IPage<String> pageAppName(int page, int size);
}
