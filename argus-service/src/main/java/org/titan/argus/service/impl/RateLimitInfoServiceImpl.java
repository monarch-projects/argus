package org.titan.argus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.titan.argus.model.dto.RateLimitConfigDTO;
import org.titan.argus.model.entities.RateLimitInfo;
import org.titan.argus.model.request.RateLimitClientDataRequest;
import org.titan.argus.service.RateLimitInfoService;
import org.titan.argus.service.util.DateUtil;
import org.titan.argus.storage.mysql.RateLimitInfoMapper;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @Title: RateLimitInfoServiceImpl
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/27
 */
@Service
@Primary
@Slf4j
public class RateLimitInfoServiceImpl extends ServiceImpl<RateLimitInfoMapper, RateLimitInfo> implements RateLimitInfoService {
    @Override
    public boolean processClientData(Collection<RateLimitClientDataRequest> requests) {
        long now = DateUtil.currentTime();

        LambdaQueryWrapper<RateLimitInfo> wrapper = new LambdaQueryWrapper<>();
        requests.forEach(r ->
                wrapper.or(q -> q.eq(RateLimitInfo::getAppName, r.getAppName()).eq(RateLimitInfo::getClzName, r.getClzName())
                        .eq(RateLimitInfo::getMethodName, r.getMethodName()))
        );
        Collection<RateLimitInfo> infos = this.list(wrapper);
        Collection<RateLimitInfo> rs = requests.stream()
                .filter(r ->
                        infos.stream().noneMatch(i -> r.getAppName().equals(i.getAppName()) && r.getClzName().equals(i.getClzName())
                                && r.getMethodName().equals(i.getMethodName())))
                .map(r ->
                        new RateLimitInfo().setAppName(r.getAppName()).setClzName(r.getClzName()).setMethodName(r.getMethodName())
                                .setLimit(r.getLimit()).setWaitTime(r.getWaitTime()).setCreated(now).setUpdated(now))
                .collect(Collectors.toList());


        return this.saveBatch(rs);
    }

    @Override
    public Collection<RateLimitConfigDTO> getConfigByAppName(String appName) {
        LambdaQueryWrapper<RateLimitInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RateLimitInfo::getAppName, appName);
        return this.list(wrapper).stream()
                .map(info ->
                        new RateLimitConfigDTO().setClzName(info.getClzName()).setMethodName(info.getMethodName())
                                .setLimit(info.getLimit()).setWaitTime(info.getWaitTime()).setId(info.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public IPage<String> pageAppName(int page, int size) {
        QueryWrapper<RateLimitInfo> wrapper = new QueryWrapper<>();
        wrapper.select("distinct app_name as appName");

        IPage<RateLimitInfo> iPage = this.page(new Page<>(page, size), wrapper);

        return new Page<String>()
                .setRecords(iPage.getRecords().stream().map(RateLimitInfo::getAppName).collect(Collectors.toList()))
                .setTotal(iPage.getTotal())
                .setCurrent(iPage.getCurrent())
                .setSize(iPage.getSize());
    }
}
