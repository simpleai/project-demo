# guava cache

## 导包

```java
<!--guava 依赖-->
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>27.0.1-jre</version>
</dependency>

```
## 定义缓存
```java

/**
 * 利用guava实现本地缓存 最大400，软引用，缓存1分钟. 没有从缓存获取到时，自动调用 load 方法获取值并存入缓存
 */
private LoadingCache<String, UserVo> productBarCodeVosCache =
        CacheBuilder.newBuilder()
        .maximumSize(400) // maximum 400 records can be cached
        .softValues() // soft reference. no RAM, clean productBarCodeVos
        .expireAfterAccess(1, TimeUnit.MINUTES) // cache will expire after 1 minutes of access
        .build(new CacheLoader<String, UserVo>(){ // build the cacheloader
@Override
public UserVo load(String userCode) {
        return getUserVoByCode(userCode);
        }
        });
```
## 没有缓存时从哪获取
```java
public UserVo getUserVoByCode(String userCode) {
        UserVo userVo = new UserVo();
        if (userCode == "admin"){
            userVo.setUserCode("admin");
            userVo.setUserName("admin");
            return userVo;
        }
        return userVo;
    }
```

## 对外暴露通过缓存获取
```java 
/**
     * 本地缓存中获取
     *
     * @param userCode
     * @return
     */
    @Override
    public UserVo getUserVoCache(String userCode){
        UserVo productBarCodeVo = null;
        try {
            productBarCodeVo = productBarCodeVosCache.get(userCode);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return productBarCodeVo;
    }
```
