package com.lzb.map;
/**
 * 测试HashMap<br/>
 * Created on : 2021-02-17 16:44
 * @author lizebin
 */
public class TestMyHashMap {

    public static void main(String[] args) {
        MyHashMap<Object, Object> map = new MyHashMap<>();
        map.put("a", "a");
        map.put("b", "b");
        map.put("b", "c");
        map.put("d", "d");

        map.put(1, 1);
        map.put(1, 2);
        map.put("jack", 2);

        for (int i=100; i<10000; i++) {
            map.put(i, i);
        }

        System.out.println(map.size());
        map.forEach((k, v) -> {
            System.out.println("k=" + k + ";v=" + v);
        });
    }

}
