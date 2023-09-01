package com.xiaoruiit.project.demo.infrastructure.driver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.Data;

/**
 * @author hanxiaorui
 * @date 2023/8/30
 */
@Data
@Table(name = "user") //对应数据库表名，如果更改表名需要同步更改数据库表名，不然会重新创建表。
public class User extends SupperEntity {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO) //mybatis-plus主键注解
    @IsKey                         //实体生成库名主键注解
    @IsAutoIncrement             //自增
    @Column                     //对应数据库字段，不配置name会直接采用属性名作为字段名
    private Long id;

    @Column(comment = "用户名", length = 64)
    private String username;
    @Column(comment = "密码", length = 64)
    private String password;
}

