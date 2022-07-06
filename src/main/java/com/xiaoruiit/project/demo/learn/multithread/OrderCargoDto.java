package com.xiaoruiit.project.demo.learn.multithread;

import lombok.Data;

/**
 * @author hanxiaorui
 * @date 2022/7/6
 */
@Data
public class OrderCargoDto {

    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 物料属性
     */
    private Integer property;
}
