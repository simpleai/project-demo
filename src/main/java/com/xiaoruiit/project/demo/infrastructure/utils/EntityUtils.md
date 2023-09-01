# EntityUtils

## 导包

## 相关类
```java
    EntityUtils
    JsonMapper
    LocalDateTimeDeserializer
    LocalDateTimeSerializer
```

## 使用

```java
    // 第一种
    UserDto user = EntityUtils.copy(user, UserDto.class);
    // 第二种
    List<UserDto> users = EntityUtils.copy(users, UserDto.class);
    // 第三种
    User user = EntityUtils.copy(object, User.class, "userId");// 待fastjson 改为jackjson

```
