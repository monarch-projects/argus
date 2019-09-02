package org.titan.argus.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.model.entities.DataBaseMonitor;
import org.titan.argus.model.request.AddDataBaseMonitorRequest;
import org.titan.argus.model.request.UpdateDataBaseMonitorRequest;
import org.titan.argus.model.response.ObjectCollectionResponse;
import org.titan.argus.model.response.ObjectDataResponse;
import org.titan.argus.service.exception.BusinessException;
import org.titan.argus.service.impl.DataBaseMonitorServiceImpl;

import java.util.Objects;

/**
 * @Title: DataBaseMonitorController
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/29
 */
@RestController
@RequestMapping("/api/v1/database/monitor")
public class DataBaseMonitorController {

    @Autowired
    private DataBaseMonitorServiceImpl monitorService;


    @PostMapping("/add")
    public ObjectDataResponse<Boolean> addMonitor(@RequestBody AddDataBaseMonitorRequest request) {
        if (Strings.isNullOrEmpty(request.getDbName()) || Strings.isNullOrEmpty(request.getHost()) || Strings.isNullOrEmpty(request.getPassword()) ||
                Strings.isNullOrEmpty(request.getUsername()) || Objects.isNull(request.getPort()))
            throw new BusinessException("参数异常！");

        return new ObjectDataResponse<>(this.monitorService.add(request));

    }

    @PostMapping("/{id}/del")
    public ObjectDataResponse<Boolean> del(@PathVariable("id") long id) {

        return new ObjectDataResponse<>(this.monitorService.removeById(id));
    }

    @GetMapping("/{id}/detail")
    public ObjectDataResponse<DataBaseMonitor> detail(@PathVariable("id") long id) {
        DataBaseMonitor monitor = this.monitorService.getById(id);
        if (Objects.isNull(monitor))
            throw new BusinessException("该内容不存在！");

        return new ObjectDataResponse<>(monitor);
    }

    @PostMapping("/update")
    public ObjectDataResponse<Boolean> update(@RequestBody UpdateDataBaseMonitorRequest request) {
        if (Strings.isNullOrEmpty(request.getDbName()) || Strings.isNullOrEmpty(request.getHost()) || Strings.isNullOrEmpty(request.getPassword()) ||
                Strings.isNullOrEmpty(request.getUsername()) || Objects.isNull(request.getPort()) || Objects.isNull(request.getId()))
            throw new BusinessException("参数异常！");

        return new ObjectDataResponse<>(this.monitorService.update(request));
    }


    @GetMapping("/page")
    public ObjectCollectionResponse<DataBaseMonitor> page(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                          @RequestParam(value = "size", required = false, defaultValue = "10") int size) {


        return new ObjectCollectionResponse<>(this.monitorService.page(new Page<>(page, size), new LambdaQueryWrapper<>()));
    }
}
