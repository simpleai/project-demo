package com.xiaoruiit.project.demo.learn.valited;

import com.xiaoruiit.project.demo.learn.valited.annotation.Mobile;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hanxiaorui
 * @date 2022/2/17
 */
@Data
public class UserMobileValid {

    @NotBlank
    private String userId;

    @Mobile
    private String mobile;

}
