package com.xiaoruiit.project.demo.learn.multithread;

import java.util.*;

/**
 * @author hxr
 */
public  class SplitUtils {
    /**
     * 将map 拆分成多个map
     *
     * @param chunkMap 被拆的 map
     * @param chunkNum 每段的大小
     * @param <k>      map 的 key类 型
     * @param <v>      map 的value 类型
     * @return List
     */
    public static <k, v> List<Map<k, v>> mapChunk(Map<k, v> chunkMap, int chunkNum) {
        if (chunkMap == null || chunkNum <= 0) {
            List<Map<k, v>> list = new ArrayList<>();
            list.add(chunkMap);
            return list;
        }
        Set<k> keySet = chunkMap.keySet();
        Iterator<k> iterator = keySet.iterator();
        int i = 1;
        List<Map<k, v>> total = new ArrayList<>();
        LinkedHashMap<k, v> tem = new LinkedHashMap<>();
        while (iterator.hasNext()) {
            k next = iterator.next();
            tem.put(next, chunkMap.get(next));
            if (i == chunkNum) {
                total.add(tem);
                tem = new LinkedHashMap<>();
                i = 0;
            }
            i++;
        }
        if (tem != null && !tem.isEmpty()){
            total.add(tem);
        }
        return total;
    }



    /**
     * @Description:集合拆分(指定集合数)
     * @Author: hxr
     * @param sourceList
     * @param count
     * @returns: List<List<T>>
     **/
    public static <T> List<List<T>> split(List<T> sourceList, int count) {
        List<List<T>> resultList = new ArrayList<>();
        int size = sourceList.size();
        if (size <= count) {
            resultList.add(sourceList);
        } else {
            int listCount = size / count;
            int last = size % count;
            for (int i = 0; i < listCount; i++) {
                List<T> itemList = new ArrayList<T>();
                for (int j = 0; j < count; j++) {
                    itemList.add(sourceList.get(i * count + j));
                }
                resultList.add(itemList);
            }
            if (last > 0) {
                List<T> itemList = new ArrayList<T>();
                for (int i = 0; i < last; i++) {
                    itemList.add(sourceList.get(listCount * count + i));
                }
                resultList.add(itemList);
            }
        }
        return resultList;
    }


    /**
     * @Description:集合拆分(指定集合大小)
     * @Author: hxr
     * @param list
     * @param len
     * @returns: List<List<T>>
     **/
    public static <T> List<List<T>> splitList(List<T> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }

        List<List<T>> result = new ArrayList<>();
        int size = list.size();
        int count = (size + len - 1) / len;

        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }


}
