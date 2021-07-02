package com.lzb.sort;

import com.sun.corba.se.impl.oa.toa.TOA;

import java.util.*;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public static void main(String[] args) {
        int total = 1000;
        Random r = new Random();
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            int age = r.nextInt(150);
            persons.add(new Person(Objects.toString(age), age));
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

        //persons.sort(Comparator.comparing(Person::getAge));

        int bucketSize = 150;
        int[] buckets = new int[bucketSize];

        for (Person p : persons) {
            int age = p.getAge();
            buckets[age] = buckets[age] + 1;
        }

        //计算出每个bucket最后一个索引
        int lastIndex = 0;
        for (int i = 0; i < bucketSize; i++) {
            int bucket = buckets[i];
            if (bucket == 0) {
                continue;
            }
            lastIndex += bucket;
            buckets[i] = lastIndex;
        }

        List<Person> tempList = new ArrayList<>(persons);
        for (Person p : tempList) {
            int age = p.getAge();
            int bucket = buckets[age];
            int index = bucket - 1;
            buckets[age] = index;
            persons.set(index, p);
        }
        tempList.clear();
    }
}
