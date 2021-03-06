package com.hengmall.user.model.platform;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

/**
 * 定位地图购物信息弹框删除 entity  请求需要的参数
 * @author Administrator
 *
 */
public class OrderDelRequest{
	
	@NotEmpty(message="定位地图购物信息弹框id不能为空")
	@ApiModelProperty(value="定位地图购物信息弹框id",required=true)
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}