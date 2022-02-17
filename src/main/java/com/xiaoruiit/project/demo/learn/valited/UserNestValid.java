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
public class UserNestValid {

    @NotBlank
    private String userId;

    private Integer age;

    @Valid
    @NotNull
    private UserDetail userDetail;

    @Data
    public static class UserDetail {

        @NotBlank
        private String currentAddress;

    }
}
