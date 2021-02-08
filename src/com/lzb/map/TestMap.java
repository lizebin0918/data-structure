package com.lzb.map;

import java.util.*;
import java.util.Map.Entry;

public class TestMap {

	public static void main(String[] args) {
		DefinitionMap<String, String> map = new LizebinHashMap20140108<String, String>();
		//Map<String, String> map = new HashMap<String, String>();
		map.put("lizebin", "linxiangjun");
		map.put("lizebin1", "linxiangjun1");
		map.put("lizebin2", "linxiangjun2");
		map.put("lizebin3", "linxiangjun3");
		map.put("lizebin4", "linxiangjun4");
		map.put("lizebin5", "linxiangjun5");
		
		for(int i=0; i<10000; i++) {
			System.out.println(i);
			map.put("" + i, "" + i);
		}
		
		int j = 0;
		for (int i = 0; i < map.size(); i++) {
			System.out.println("输出：" + map.get("" + i));
			j++;
		}
		System.out.println("j = " + j);
		/*System.out.println(map.containsKey("lizebin"));
		System.out.println(map.containsKey("lizebin1"));
		System.out.println(map.containsKey("lizebin2"));
		map.remove("lizebin");
		map.remove("lizebin1");
		map.remove("lizebin2");
		System.out.println(map.containsKey("lizebin"));
		System.out.println(map.containsKey("lizebin1"));
		System.out.println(map.containsKey("lizebin2"));
		map.put("lizebin", "linxiangjun");
		map.put("lizebin", "linxiangjunLizebin");
		System.out.println(map.containsKey("lizebin"));
		System.out.println(map.get("lizebin"));
		System.out.println(map.get("lizebin1"));
		System.out.println(map.get("lizebin2"));
		System.out.println(map.get("lizebin3"));
		System.out.println(map.get("lizebin4"));*/
		
		System.out.println(map.size());
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("1", "aa");
		m.put("2", "bb");
		Set<Entry<String, String>> entrySet = m.entrySet();
		Iterator<Entry<String, String>> i = entrySet.iterator();
		while(i.hasNext()) {
			System.out.println(i.next());
			i.remove();
		}
		
		
		
	}
}
