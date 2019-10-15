package org.titan.argus.auth.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @author starboyate
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors
public class DeptVO extends Tree<DeptVO> {
	private String name;
}
