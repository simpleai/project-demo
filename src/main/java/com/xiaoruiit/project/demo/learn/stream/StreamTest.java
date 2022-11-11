package com.xiaoruiit.project.demo.learn.stream;

import com.xiaoruiit.project.demo.utils.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hanxiaorui
 * @date 2022/11/11
 */
@Slf4j
public class StreamTest {

    public static void main(String[] args) {
        List<User> users = initUsers();

        log.info("users:{}", JSON.toJSONString(users));
        Map<String, User> userCodeUserMap = listToMap(users);
        log.info("userCodeUserMap:{}", JSON.toJSONString(userCodeUserMap));
    }

    /**
     * list 转 map
     * @param users
     * @return
     */
    public static Map<String, User> listToMap(List<User> users){
        if (users == null){
            return new HashMap<String, User>();
        }
        // 解决key重复 (k1,k2) -> k1
        Map<String, User> userCodeUserMap = users.stream().collect(Collectors.toMap(User::getUserCode, user -> user, (k1,k2) -> k1));
        return userCodeUserMap;
    }

    /**
     * 分组 todo
     * @return
     */

    /**
     * 过滤 todo
     * @return
     */

    private static List<User> initUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.userCode = "U" + i;
            user.userName = "zs" + i;
            user.age = 10;
            users.add(user);
        }
        return users;
    }

    @Data
    static class User {
        String userCode;

        String userName;

        Integer age;
    }

}
