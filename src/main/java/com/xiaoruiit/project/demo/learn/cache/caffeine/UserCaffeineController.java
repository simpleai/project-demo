package com.xiaoruiit.project.demo.learn.cache.caffeine;

import com.xiaoruiit.project.demo.common.Result;
import com.xiaoruiit.project.demo.utils.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hanxiaorui
 * @date 2022/7/25
 */
@Slf4j
@RestController
@RequestMapping("/caffeine/user")
public class UserCaffeineController {
    @Autowired
    private UserCaffeineService userService;

    @GetMapping("/getUser")
    public Result<User> findUserById(@RequestParam("code") String userCode) {
        User user = userService.findUserById(userCode);
        log.info(JSON.toJSONString(user));
        return Result.success(user);
    }

    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user) {
        userService.putUser(user);
        return Result.success();
    }

    @DeleteMapping("/deleteUser")
    public Result deleteUser(@RequestParam("code") String code) {
        userService.clearUserCaffeine(code);
        return Result.success(code);
    }

}
