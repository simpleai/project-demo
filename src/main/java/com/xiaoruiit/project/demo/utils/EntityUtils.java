package com.xiaoruiit.project.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.xiaoruiit.project.demo.exception.BizException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class EntityUtils {
    private static Logger log = LoggerFactory.getLogger(EntityUtils.class);

    public EntityUtils() {
    }

    public static <R> List<R> copy(Collection<?> source, Class<R> target) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        } else {
            ObjectMapper mapper = JsonMapper.INSTANCE.mapper();

            try {
                JavaType type = mapper.getTypeFactory().constructParametricType(List.class, new Class[]{target});
                return (List)mapper.readValue(mapper.writeValueAsString(source), type);
            } catch (JsonProcessingException var4) {
                throw new BizException(var4.getMessage(), var4);
            }
        }
    }

    public static <T, R> R copy(T source, Class<R> target) {
        if (null == source) {
            return null;
        } else {
            ObjectMapper mapper = JsonMapper.INSTANCE.mapper();

            try {
                return mapper.readValue(mapper.writeValueAsString(source), target);
            } catch (JsonProcessingException var4) {
                throw new BizException(var4.getMessage(), var4);
            }
        }
    }

//    public static <T, R> R copy(T source, Class<R> target, String... excludeFields) {
//        if (null == source) {
//            return null;
//        } else if (null != excludeFields && excludeFields.length != 0) {
//            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(new String[0]);
//            filter.getExcludes().addAll(Arrays.asList(excludeFields));
//            return JSON.parseObject(JSON.toJSONString(source, filter, new SerializerFeature[0]), target);
//        } else {
//            return copy(source, target);
//        }
//    }

    public static <R, T> R copyProperties(T source, Class<R> target, String... excludeFields) {
        if (null == source) {
            return null;
        } else {
            try {
                R targetBean = target.newInstance();
                BeanUtils.copyProperties(source, targetBean, excludeFields);
                return targetBean;
            } catch (Exception var4) {
                throw new BizException(var4.getMessage(), var4);
            }
        }
    }

    public static <R, T> List<R> copyProperties(Collection<T> source, Class<R> target, String... excludeFields) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        } else {
            List<R> list = (List)((Collection)Optional.of(source).orElse(Collections.emptyList())).stream().map((n) -> {
                return copyProperties(n, target, excludeFields);
            }).collect(Collectors.toList());
            return list;
        }
    }

    public static <R, T> List<R> copyProperties(Collection<T> source, Class<R> target) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        } else {
            List<R> list = (List)((Collection)Optional.of(source).orElse(Collections.emptyList())).stream().map((n) -> {
                return copyProperties(n, target);
            }).collect(Collectors.toList());
            return list;
        }
    }

    public static Map<String, Object> copyToMap(Object object, boolean ignoreNull) {
        if (null == object) {
            return null;
        } else {
            try {
                Map<String, Object> map = new HashMap();
                Field[] var3 = object.getClass().getDeclaredFields();
                int var4 = var3.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Field field = var3[var5];
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object o = field.get(object);
                    if (ignoreNull) {
                        if (null != o) {
                            map.put(field.getName(), o);
                        }
                    } else {
                        map.put(field.getName(), o);
                    }

                    field.setAccessible(flag);
                }

                return map;
            } catch (Exception var9) {
                log.error(var9.getMessage(), var9);
                return null;
            }
        }
    }

    public static Map<String, Object> notNullCastToMap(Object object) {
        return copyToMap(object, true);
    }
}
