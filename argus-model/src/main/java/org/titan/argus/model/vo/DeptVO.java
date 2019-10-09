package org.titan.argus.model.vo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author starboyate
 */
@Data
@Accessors(chain = true)
public class DeptVO implements Serializable {
	private Long id;

	private String name;

	private Integer order;

	private Long parentId;

	private List<DeptVO> children = new ArrayList<>();
}
