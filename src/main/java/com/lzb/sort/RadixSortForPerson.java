package com.lzb.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 对Person.age基数排序<br/>
 * Created on : 2021-06-22 23:07
 *
 * @author lizebin
 */
public class RadixSortForPerson {

    private static class Person {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    public static void main(String[] args) {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            persons.add(new Person(Objects.toString(i), i));
        }

        Collections.shuffle(persons);
        sort(persons);
        persons.forEach(p -> {
            System.out.println("name=" + p.name + ";age=" + p.age);
        });
    }

    /**
     * 按照年龄排序
     * @param persons
     */
    public static void sort(List<Person> persons) {

    }

}
