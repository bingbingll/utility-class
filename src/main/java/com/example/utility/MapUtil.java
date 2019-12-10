package com.example.utility;

import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@SuppressWarnings("all")
@Component
public class MapUtil {
    /**
     * TODO: map key大写字母转换为小写
     *
     * @param orgMap :
     * @return void
     * @author 李兵
     * @date 2019/6/22 16:29
     **/
    public static <K, V> Map<K, V> newTransformUpperCaseByMap(@NotNull Map<K, V> orgMap) {
        Map<K, V> resultMap = new HashMap<>();
        if (ObjectUtils.isEmpty(orgMap)) {
            return resultMap;
        }
        if (orgMap.isEmpty() || orgMap.equals(null) || orgMap == null) {
            return resultMap;
        }
        Set<K> keySet = orgMap.keySet();
        for (Object key : keySet) {
            String newKey = key.toString().toLowerCase();
            newKey = newKey.replace("_", "");
            resultMap.put((K) newKey, orgMap.get(key));
        }
        return resultMap;
    }


    public static <K, V> List<Map<K, V>> newTransformUpperCaseByList(List<Map<K, V>> arrayList) {
        List<Map<K, V>> res = new ArrayList<>();
        if (arrayList.size() <= 0) {
            return res;
        }
        for (Map<K, V> objectObjectMap : arrayList) {
            res.add(newTransformUpperCaseByMap(objectObjectMap));
        }
        return res;
    }


    /**
     * TODO: map深度数据复制
     *
     * @param obj :
     * @return T
     * @author 李兵
     * @date 2019/6/21 10:20
     **/
    public static <T extends Serializable> T clone(T obj) {
        T clonedObj = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            clonedObj = (T) ois.readObject();
            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clonedObj;
    }


    public static Object[][] listToArray(List<LinkedHashMap<String, Object>> list, int KeyLenght) {
        if (CollectionUtils.isEmpty(list)) {
            return new Object[0][];
        }
        int size = list.size();
        Object[][] array = new Object[size][KeyLenght];
        //循环遍历所有行
        for (int i = 0; i < size; i++) {
            //每行的列数
            array[i] = list.get(i).values().toArray();
        }
        return array;
    }


    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        if (map == null) {
            return bean;
        }
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
     *
     * @param objList
     * @return
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (objList != null && objList.size() > 0) {
            objList.forEach(l -> {
                T bean = l;
                Map<String, Object> map = beanToMap(bean);
                list.add(map);
            });
        }
        return list;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps
     * @param clazz
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        maps.forEach(m -> {
            T bean = null;
            try {
                bean = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            mapToBean(m, bean);
            list.add(bean);
        });
        return list;
    }

    public static <T> List<T> lkmapsToObjects(List<LinkedHashMap<String, Object>> lkmaps, Class<T> clazz) {
        LinkedList<T> list = new LinkedList<>();
        if (lkmaps != null && lkmaps.size() > 0) {
            lkmaps.forEach(x -> {
                T bean = null;
                try {
                    bean = clazz.newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
                mapToBean(x, bean);
                list.add(bean);
            });
        }
        return list;
    }


}
