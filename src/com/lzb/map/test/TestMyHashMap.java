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

        for (int i=1; i<=30; i++) {
            map.put(i, i);
        }

        map.print();

        LinkedHashMap<String, String> linkedMap = new LinkedHashMap<>();
        linkedMap.put("a", "a");
        linkedMap.put("b", "b");
        linkedMap.put("c", "c");
        linkedMap.put("d", "d");
        System.out.println(linkedMap);

        LinkedList<Integer> l = new LinkedList<>();
        for (int i=0;;++i) {
            l.add(i);
            if (i >= 8 - 1) {
                System.out.println("转成红黑树:" + l.size());
                break;
            }
        }
    }

}
