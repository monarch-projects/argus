package org.titan.argus.storage.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.titan.argus.model.entities.RateLimitInfo;

/**
 * @Title: RateLimitInfoMapper
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/27
 */
@Mapper
public interface RateLimitInfoMapper extends BaseMapper<RateLimitInfo> {
}
