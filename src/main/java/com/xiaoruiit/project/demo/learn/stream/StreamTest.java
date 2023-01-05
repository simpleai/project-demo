package com.xiaoruiit.project.demo.learn.stream;

import com.xiaoruiit.project.demo.utils.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

        //Integer add = add(users);
        //System.out.println(add);

        updateList(users);
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
     * 分组
     * @return
     */
    public static Map<Integer, List<User>> groupBy(List<User> users){
        Map<Integer, List<User>> ageUserGroupMap = users.stream().collect(Collectors.groupingBy(User::getAge));

        return ageUserGroupMap;
    }

    /**
     * 过滤 todo
     * @return
     */

    /**
     * 某个字段求和
     * @return
     */
    public static Integer add(List<User> users){
        // 1.
        Integer sumAge = users.stream().map(User::getAge).reduce(0, Integer::sum);

        // 2.
        Integer sumAge2 = users.stream().map(User::getAge).reduce(Integer::sum).get();

        // 3.
        Integer sumAge3 = users.stream().collect(Collectors.summingInt(User::getAge));


        return sumAge2;
    }

    /**
     * 排序 todo
     * @return
     */

    /**
     * 转map
     * @return
     */
    public static Map<String, User> toMap(List<User> users){
        // 1.
        Map<String, User> userCodeUserMap = users.stream().collect(Collectors.toMap(User::getUserCode, o -> o));

        //2.
        Map<String, User> userCodeUserMap2 = users.stream().collect(Collectors.toMap(User::getUserCode, Function.identity()));

        return userCodeUserMap;
    }

    /**
     * 参与stream中的变量不能被修改过
     * @return
     */
    public static List<User> updateList(List<User> users){
        Map<String, Integer> actualReceiveMap = new HashMap<>();

        if (users != null){
            actualReceiveMap = users.stream().collect(Collectors.toMap(User::getUserCode, User::getAge));//
        }

        Map<String, Integer> finalActualReceiveMap = actualReceiveMap;// actualReceiveMap 不能被修改过
        users.forEach(user -> {
            Integer a = finalActualReceiveMap.get("a");
        });

        return users;
    }

    private static List<User> initUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 0; i++) {
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
