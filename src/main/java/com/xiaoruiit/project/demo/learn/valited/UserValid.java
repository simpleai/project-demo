package com.xiaoruiit.project.demo.learn.valited;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hanxiaorui
 * @date 2022/2/17
 */
@Data
public class UserValid {

    @NotBlank
    private String userId;

    @NotNull
    private Integer age;

}
