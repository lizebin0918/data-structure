package com.lzb.list;

import java.util.LinkedList;
import java.util.*;

public class TestList {
	public static void main(String[] args) {
		//DefinitionList<String> list = new LizebinList<String>();
		/*list.add("li");
		list.add("ze");
		list.add("bin");*/
		// list.set(0, "LinXiangJun");
		// list.add(3, "lin");
		// list.set(0, "xiangjun");
		// System.out.println(list.size());
		// printList(list);
		// list.clear();
		/*DefinitionList<String> list = new LizebinLinkedList<String>();
		list.add("li");
		list.add("ze");
		list.add("bin");
		Iterator<String> i = list.iterator();
		while(i.hasNext()) {
			System.out.println(i.next());
		}*/
		List<String> l = new LinkedList<String>();
		l.add("1");
		l.add("2");
		l.add("3");
		Iterator<String> i = l.iterator();
		while(i.hasNext()) {
			//System.out.println(i.next());
			i.next();
			l.remove(1);
			i.remove();
		}
		
		 if (1==1)
            System.out.println(1);
         	      else
         System.out.println(2);
		 System.out.println(3);
		 System.out.println(4);
	}
	
	public static void printList(MyList<String> list) {
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}