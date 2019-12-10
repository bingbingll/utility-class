package com.example.utility;

import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@SuppressWarnings("all")
public class MapUtil {
    /**
     * TODO: map key大写字母转换为小写
     *
     * @param orgMap :
     * @return void
     * @author 李兵
     * @date 2019/6/22 16:29
     **/
    @Deprecated
    public static Map<String, Object> transformUpperCaseByMap(Map<String, Object> orgMap) {
        Map<String, Object> resultMap = new HashMap<>();
        if (orgMap.isEmpty() || orgMap.equals(null)) {
            return resultMap;
        }
        Set<String> keySet = orgMap.keySet();
        for (Object key : keySet) {
            String newKey = key.toString().toLowerCase();
            newKey = newKey.replace("_", "");
            resultMap.put(newKey, orgMap.get(key));
        }
        return resultMap;
    }

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

    /**
     * TODO: list map key转换为小写
     *
     * @param orgListMap :
     * @return void
     * @author 李兵
     * @date 2019/6/22 16:28
     **/
    @Deprecated
    public static List<Map<String, Object>> transformUpperCaseByList(List<Map<String, Object>> orgListMap) {
        List<Map<String, Object>> res = new ArrayList<>();
        if (orgListMap.size() <= 0) {
            return res;
        }
        for (Map<String, Object> objectObjectMap : orgListMap) {
            res.add(transformUpperCaseByMap(objectObjectMap));
        }
        return res;
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


    /**
     * TODO: list 中map重组
     *
     * @param lmps   : 数据库中查询的数据
     * @param xNames : 定义x轴的名称
     * @return java.util.List
     * @author 李兵
     * @date 2019/6/20 18:20
     **/
    public static List<Map<String, Object>> congZhu(List<LinkedHashMap<String, Object>> lmps, Object[] xNames, int xnameCount) {
        List<Map<String, Object>> rests = new ArrayList<>();

        for (Object name : xNames) {
            LinkedHashMap<String, Object> linkedHashMap = ist(lmps, name, xnameCount);
            if (ObjectUtils.isNotEmpty(linkedHashMap)) {
                rests.add(linkedHashMap);
            }
        }
        return rests;
    }

    /**
     * TODO: 验证list中的map对象是否存指定的值
     *
     * @param lmps :
     * @param name :
     * @return java.util.LinkedHashMap
     * @author 李兵
     * @date 2019/6/20 18:16
     **/
    private static LinkedHashMap<String, Object> ist(List<LinkedHashMap<String, Object>> lmps, Object name, int xnameCount) {
        //定义一个空的对象
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>(1);
        boolean ist = true;
        Set<String> keySet = new HashSet();

        for (LinkedHashMap lmp : lmps) {
            keySet = lmp.keySet();
            if (lmp.containsValue(name)) {
                ist = false;
                // TODO: 注意此次为深度克隆，不能使用=复制，putAll 等。若原始map的值发生变化，目标map的值也会变化。
                linkedHashMap = MapUtil.clone(lmp);
                break;
            }
        }
        //循环所有的list中map 发现没有存在当前值时进行存放
        if (ist) {
            Iterator<String> iterator1 = keySet.iterator();
            if (!iterator1.hasNext()) {
                //进入表示 lmps 集合中没有数据
                linkedHashMap.put("xname", name);
                switch (xnameCount) {
                    case 1:
                        linkedHashMap.put("val1", 0L);
                        break;
                    case 2:
                        linkedHashMap.put("val1", 0L);
                        linkedHashMap.put("val2", 0L);
                        break;
                    case 3:
                        linkedHashMap.put("val1", 0L);
                        linkedHashMap.put("val2", 0L);
                        linkedHashMap.put("val3", 0L);
                        break;
                    case 4:
                        linkedHashMap.put("val1", 0L);
                        linkedHashMap.put("val2", 0L);
                        linkedHashMap.put("val3", 0L);
                        linkedHashMap.put("val4", 0L);
                        break;
                    default:
                        linkedHashMap.put("val", 0);
                        break;
                }
            }
            while (iterator1.hasNext()) {
                String nextKey = iterator1.next();
                //列名缺少补充
                if ("xname".equals(nextKey)) {
                    linkedHashMap.put(nextKey, name);
                } else if ("status".equals(nextKey)) {
                    linkedHashMap.put(nextKey, name);
                } else if ("name".equals(nextKey)) {
                    linkedHashMap.put(nextKey, name);
                } else {
                    linkedHashMap.put(nextKey, 0);
                }
            }
        }
        return linkedHashMap;
    }


    /**
     * TODO:
     *
     * @param list      :
     * @param KeyLenght :
     * @return java.lang.Object[][]
     * @author 李兵
     * @date 2019/6/21 10:20
     **/
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
     * 获取title
     *
     * @param queryDatas
     * @return
     */
    public static List<String> getTitile(List<LinkedHashMap<String, Object>> queryDatas, String key) {
        Set<String> collect = queryDatas.stream().map(e -> e.get(key) + "").collect(Collectors.toSet());
        return new ArrayList<String>(collect);
    }

    public static List<Map<String, Object>> congZhu2(List<LinkedHashMap<String, Object>> queryDatas, List<String> titles,
                                                     Object[] xNames, String key) {
        List<Map<String, Object>> rests = new ArrayList<>();
        for (String title : titles) {
            for (Object name : xNames) {
                LinkedHashMap<String, Object> orElse = queryDatas.stream().filter(e -> e.containsValue(title) && e.containsValue(name)).findFirst().orElse(null);
                if (orElse != null) {
                    rests.add(orElse);
                } else {
                    LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
                    map.put("CREATE_DATE", name);
                    map.put("PARENT_NAME", title);
                    map.put(key, 0);
                    rests.add(map);
                }
            }
        }
        return rests;
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
        List<T> list = new ArrayList<>();
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
