package org.titan.argus.model.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Objects;

/**
 * @Title: ObjectCollectionResponse
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/28
 */
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectCollectionResponse<T> {
    private int code = 200;
    private String message = "OK";
    private Collection<T> data;

    private long total = 0;
    private long page = 0;
    private long size = 0;

    public ObjectCollectionResponse(Collection<T> data) {
        this.data = data;
        if (Objects.nonNull(data)) {
            this.size = data.size();
            this.total = this.size;
        }
    }


    public ObjectCollectionResponse(IPage<T> data) {
        if (Objects.nonNull(data)) {
            this.data = data.getRecords();
            this.total = data.getTotal();
            this.size = data.getSize();
            this.page = data.getCurrent();
        }
    }

}
