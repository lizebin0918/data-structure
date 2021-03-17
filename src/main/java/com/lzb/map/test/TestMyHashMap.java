package com.lzb.map.test;

import com.lzb.map.MyHashMap;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * 测试HashMap<br/>
 * Created on : 2021-02-17 16:44
 * @author lizebin
 */
public class TestMyHashMap {

    public static void main(String[] args) {
        MyHashMap<Integer, Integer> map = new MyHashMap<>();

        for (int i=1; i<=100; i++) {
            map.put(i, i);
        }

        map.print();

        LinkedHashMap<String, String> linkedMap = new LinkedHashMap<>();
        linkedMap.put("a", "a");
        linkedMap.put("b", "b");
        linkedMap.put("c", "c");
        linkedMap.put("d", "d");
        System.out.println(linkedMap);

        System.out.println(map.size());
        System.out.println(map.get(1));
        System.out.println(map.get(11));
        System.out.println(map.get(12));
        System.out.println(map.get(79));
        System.out.println(map.get(99));

        System.out.println(map.keySet());
        System.out.println(map.values());
    }

}
