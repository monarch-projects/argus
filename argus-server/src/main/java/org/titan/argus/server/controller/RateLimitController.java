package org.titan.argus.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.model.dto.RateLimitConfigDTO;
import org.titan.argus.model.entities.RateLimitInfo;
import org.titan.argus.model.message.EventBusMessage;
import org.titan.argus.model.message.UpdateRateLimitMessage;
import org.titan.argus.plugin.ratelimit.commons.util.EventBusUtil;
import org.titan.argus.server.response.ObjectCollectionResponse;
import org.titan.argus.server.response.ObjectDataResponse;
import org.titan.argus.service.RateLimitInfoService;
import org.titan.argus.service.exception.BusinessException;
import org.titan.argus.service.util.DateUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Title: RateLimitController
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/27
 */
@RestController
@RequestMapping("/api/v1/ratelimit")
@Api("限流相关controller")
public class RateLimitController {

    @Autowired
    private RateLimitInfoService infoService;
    @Autowired
    private EventBusUtil busUtil;


    @GetMapping("/appName/page")
    @ApiOperation("分页查询限流的app name")
    public ObjectCollectionResponse<String> pageAppNames(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                         @RequestParam(value = "size", required = false, defaultValue = "20") int size) {

        return new ObjectCollectionResponse<>(this.infoService.pageAppName(page, size));
    }


    @GetMapping("/config/{id}/update")
    @ApiOperation("修改限流策略")
    /**
     * 修改限流策略，修改后会将限流策略推送到客户端
     */
    public ObjectDataResponse<Boolean> update(@PathVariable("id") long id, @RequestParam("limit") int limit, @RequestParam("waitTime") int waitTime) {

        RateLimitInfo info = this.infoService.getById(id);
        if (Objects.isNull(info))
            throw new BusinessException("该数据不存在！");

        info.setUpdated(DateUtil.currentTime()).setLimit(limit).setWaitTime(waitTime);

        if (this.infoService.updateById(info)) {
            List<RateLimitConfigDTO> configs = Collections.singletonList(new RateLimitConfigDTO().setClzName(info.getClzName()).setMethodName(info.getMethodName())
                    .setLimit(info.getLimit()).setWaitTime(info.getWaitTime()));

            this.busUtil.sendMessage(EventBusMessage.RATE_LIMIT_UPDATE, new UpdateRateLimitMessage().setConfigs(configs));

            return new ObjectDataResponse<>(true);

        }
        return new ObjectDataResponse<>(false);
    }

    @ApiOperation("根据app name 分页查询 限流策略")
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
