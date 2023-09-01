package com.xiaoruiit.project.demo.infrastructure.driver.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.ColumnType;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.util.Date;

/**
 * @author hanxiaorui
 * @date 2023/8/30
 */
@Data
public class SupperEntity {

    /**
     * 创建时间
     */
    @Column(comment = "创建时间")
    @ColumnType(value = MySqlTypeConstant.DATETIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 最后修改时间
     */
    @Column(comment = "最后修改时间")
    @ColumnType(value = MySqlTypeConstant.DATETIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableLogic
    @Column(comment = "是否删除:0表示未删除 1表示删除.")
    private Boolean deleted;
}

