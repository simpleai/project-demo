package com.xiaoruiit.project.demo.learn.valited;

import com.xiaoruiit.project.demo.common.Result;
import com.xiaoruiit.project.demo.learn.valited.factory.UserValidFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author hanxiaorui
 * @date 2022/2/17
 */
@RestController()
@RequestMapping("/valited")
@Validated
public class ValitedTestController {

    // get请求 @Validated在方法和入参上校验不生效，需要放在类上
    @GetMapping("/getUser")
    public Result<UserValid> demoUser(@NotBlank @RequestParam(value = "userId") String userId){
        return Result.success(UserValidFactory.buildUserValid());
    }

    // get请求 @Validated在方法和入参上校验不生效，需要放在类上
    @GetMapping("/getUserByCondition")
    public Result<UserValid> demoUser(UserValid userValid){
        return Result.success(UserValidFactory.buildUserValid());
    }

    // post请求 @Validated在类和方法上不生效，需要放在入参上
    @PostMapping("/addUser")
    public Result addUser(@Validated @RequestBody UserValid userValid){
        return Result.success();
    }

    /**
     * 嵌套对象校验
     */
    // post请求 @Validated在类和方法上不生效，需要放在入参上
    @PostMapping("/addUser2")
    public Result addUser2(@Validated @RequestBody UserNestValid userNestValid){
        return Result.success();
    }

    /**
     * List校验
     */
    @PostMapping("/addUsers")
    public Result addUsers(@Validated @RequestBody List<UserNestValid> userNestValids){
        return Result.success();
    }

    /**
     * 自定义注解手机号校验
     */
    @PostMapping("/addUser3")
    public Result addUsers(@Validated @RequestBody UserMobileValid userMobileValid){
        return Result.success();
    }
}
