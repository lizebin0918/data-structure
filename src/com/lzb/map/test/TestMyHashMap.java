package com.lzb.map.test;

import com.lzb.map.MyHashMap;
import com.lzb.map.test.model.Key;

/**
 * 测试HashMap<br/>
 * Created on : 2021-02-17 16:44
 * @author lizebin
 */
public class TestMyHashMap {

    public static void main(String[] args) {
        MyHashMap<Object, Integer> map = new MyHashMap<>();

        for (int i = 1; i <= 10; i++) {
            map.put(new Key(i), i);
        }
        for (int i = 5; i <= 7; i++) {
            Asserts.test(map.remove(new Key(i)) == i);
        }

        System.out.println(map.size());
        Asserts.test(map.size() == 7);
        for (int i = 1; i <= 3; i++) {
            map.put(new Key(i), i + 5);
        }
        map.print();
    }

}
