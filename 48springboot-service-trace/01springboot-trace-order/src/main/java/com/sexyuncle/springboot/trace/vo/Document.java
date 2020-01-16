package com.sexyuncle.springboot.trace.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * Copyright: (C), 2020/1/6 17:34
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("文章")
public class Document {
	
	@ApiModelProperty(value="文章ID", required=true)
	private Long id;
	
	@ApiModelProperty(value="文章标题", required=true)
	private String title;
}
