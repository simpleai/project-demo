package com.xiaoruiit.project.demo.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Bean拷贝
 *
 * @author hanxiaorui
 * @date 2022/02/17
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * 拷贝集合属性
     *
     * 使用场景：Entity、Bo、Vo层数据的复制，因为BeanUtils.copyProperties只能给目标对象的属性赋值，却不能在List集合下循环赋值，因此添加该方法
     * 如：List<AdminEntity> 赋值到 List<AdminVo> ，List<AdminVo>中的 AdminVo 属性都会被赋予到值
     * S: 数据源类 ，T: 目标类::new(eg: AdminVo::new)
     * @author hanxiaorui
     * @date 2022/02/17
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        if (null == sources) {
            return Collections.emptyList();
        }

        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source, t);
            list.add(t);
        }
        return list;
    }

}
