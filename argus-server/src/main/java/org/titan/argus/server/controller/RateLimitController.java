package org.titan.argus.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.model.dto.RateLimitConfigDTO;
import org.titan.argus.model.entities.RateLimitInfo;
import org.titan.argus.model.request.RateLimitClientDataRequest;
import org.titan.argus.model.response.ObjectCollectionResponse;
import org.titan.argus.model.response.ObjectDataResponse;
import org.titan.argus.service.RateLimitInfoService;
import org.titan.argus.service.exception.BusinessException;
import org.titan.argus.service.util.DateUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Title: RateLimitController
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/27
 */
@RestController
@RequestMapping("/api/v1/ratelimit")
public class RateLimitController {

    @Autowired
    private RateLimitInfoService infoService;

    private ReentrantLock lock = new ReentrantLock();

    @PostMapping("/client/data")
    public ObjectDataResponse<Boolean> processClientData(@RequestBody Collection<RateLimitClientDataRequest> requests) {
        if (Objects.isNull(requests))
            throw new BusinessException("参数异常");
        try {
            lock.lock();
            return new ObjectDataResponse<>(this.infoService.processClientData(requests));
        } finally {
            lock.unlock();
        }
    }

    @GetMapping("/{appName}/config/get")
    public ObjectCollectionResponse<RateLimitConfigDTO> getConfigByAppName(@PathVariable("appName") String appName) {
        if (Strings.isNullOrEmpty(appName))
            throw new BusinessException("参数异常");

        return new ObjectCollectionResponse<>(this.infoService.getConfigByAppName(appName));
    }

    @GetMapping("/appName/page")
    public ObjectCollectionResponse<String> pageAppNames(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                         @RequestParam(value = "size", required = false, defaultValue = "20") int size) {

        return new ObjectCollectionResponse<>(this.infoService.pageAppName(page, size));
    }


    @GetMapping("/config/{id}/update")
    public ObjectDataResponse<Boolean> update(@PathVariable("id") long id, @RequestParam("limit") int limit, @RequestParam("waitTime") int waitTime) {

        return new ObjectDataResponse<>(this.infoService.updateById(new RateLimitInfo().setId(id).setLimit(limit)
                .setWaitTime(waitTime).setUpdated(DateUtil.currentTime())));
    }

    @GetMapping("/config/{appName}/page")
    public ObjectCollectionResponse<RateLimitInfo> pageConfigByAppName(@PathVariable("appName") String appName,
                                                                       @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                       @RequestParam(value = "size", required = false, defaultValue = "20") int size) {

        if (Strings.isNullOrEmpty(appName))
            throw new BusinessException("参数异常");

        return new ObjectCollectionResponse<>(this.infoService.page(new Page<>(page, size), new LambdaQueryWrapper<RateLimitInfo>()
                .select(RateLimitInfo::getId, RateLimitInfo::getClzName, RateLimitInfo::getMethodName, RateLimitInfo::getLimit, RateLimitInfo::getWaitTime)
                .eq(RateLimitInfo::getAppName, appName)));
    }
}
