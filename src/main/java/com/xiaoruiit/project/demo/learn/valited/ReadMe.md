# 参数校验使用

## 导包

```java
<!--valid 和 hibernate-validator包含在其中-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```
## GET请求校验
controller加@Validated

```java
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class ValitedTestController {

}
```

## POST请求实体校验
入参加@Validated

```java
@PostMapping("/addUser")
public Result addUser(@Validated @RequestBody UserValid userValid){
    return Result.success();
}
```

## 单属性校验

```java
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NotBlank
@NotNull
@Min(0)
@Max(1000)

```
### 入参
```java
@GetMapping("/getUser")
public Result<UserValid> demoUser(@NotBlank @RequestParam(value = "userId") String userId){
    return Result.success(UserValidFactory.buildUserValid());
}
```
### 对象属性
```java
@Data
public class UserValid {

    @NotBlank
    private String userId;

    @NotNull
    private Integer age;

}
```
## 嵌套对象校验

```java
嵌套对象加 @Valid

@Data
public class UserNestValid {

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
```

## 自定义注解校验
annotation

## 参数校验异常抓取
ParamExceptionHandle