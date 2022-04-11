# 传递调用者信息到下游服务公共组件

调用下游使用feign

## 环境
```java
<dependency>
    <groupId>io.github.openfeign</groupId>
    <artifactId>feign-httpclient</artifactId>
    <version>10.10.1</version>
</dependency>
```

## 将调用者信息放入请求头，带到下游服务

```java
/**
 * @author hxr
 * @since 2022/4/9
 */
@Slf4j
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {


    /**
     * 每次请求解析token放入对象，并存到当前线程的变量ThreadLocal
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserCode("U123");
        userInfo.setUserName("admin");
        UserInfo.set(userInfo);
        return true;
    }

    /**
     * 请求结束清除线程变量
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfo.clean();
    }
}
```

```java
/**
 * @author hanxiaorui
 * @date 2022/4/9
 */
@Data
public class UserInfo {

    /**
     * 保证线程安全
     */
    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new NamedThreadLocal<>("userContext");

    private String userCode;

    private String userName;

    public static UserInfo current() {
        return THREAD_LOCAL.get();
    }

    public static void clean() {
        THREAD_LOCAL.remove();
        THREAD_LOCAL.set(null);
    }

    public static void set(UserInfo userInfo) {
        THREAD_LOCAL.set(userInfo);
    }
}
```

```java
/**
 * 所有的feign调用之前添加调用者信息
 */
@Slf4j
@Configuration
public class FeignHeaderConfiguration implements RequestInterceptor {

    /**
     * 会拦截两次，第一次无效
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            return;
        }

        UserInfo userInfo = UserInfo.current();
        if (userInfo == null) {
            return;
        }

        try {
            template.header("from", "demo");
            template.header("userCode", userInfo.getUserCode());
            template.header("userName", URLEncoder.encode(userInfo.getUserName(), StandardCharsets.UTF_8.toString()));// 乱码

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
```
## 下游服务用拦截器在实际处理前获取请求头，放入本地线程变量

```java
/**
 * @author hanxiaorui
 * @date 2022/4/9
 */
@Data
public class CallerInfo {

    /**
     * 线程安全
     */
    private final static ThreadLocal<CallerInfo> REQUEST_HEADER_THREAD_LOCAL_CALLERINFO = new ThreadLocal<>();

    private String userCode;

    private String userName;

    public static void setCallerInfo(CallerInfo callerInfo){
        REQUEST_HEADER_THREAD_LOCAL_CALLERINFO.set(callerInfo);
    }

    public static CallerInfo getCallerInfo(){
        return REQUEST_HEADER_THREAD_LOCAL_CALLERINFO.get();
    }

    public static void removeCallerInfo(){
        REQUEST_HEADER_THREAD_LOCAL_CALLERINFO.remove();
    }
}
```

```java
/**
 * @author hanxiaorui
 * @date 2022/4/9
 */
@Slf4j
@Component
public class CallerInfoInterceptor extends HandlerInterceptorAdapter {

    /**
     * 请求被处理前，拦截器拦截，提取调用者信息，放入local
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String userName = URLDecoder.decode(request.getHeader("userName"), StandardCharsets.UTF_8.toString());// URLDecoder解决乱码
            String userCode = request.getHeader("userCode");

            CallerInfo callerInfo = new CallerInfo();
            callerInfo.setUserCode(userCode);
            callerInfo.setUserName(userName);
            CallerInfo.setCallerInfo(callerInfo);// 放入local

            log.debug(CallerInfo.getCallerInfo().toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullPointerException e){

        }
        return true;
    }

    /**
     * 调用结束后清除线程中调用者信息，防止在线程池中出问题
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        CallerInfo.removeCallerInfo();
    }
}
```

```java
/**
 * @author hanxiaorui
 * @date 2022/4/9
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public CallerInfoInterceptor requestHeaderContextInterceptor() {
        return new CallerInfoInterceptor();
    }

    /**
     * 绑定拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestHeaderContextInterceptor())
                .addPathPatterns("/**");// 拦截所有请求
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
```
