package com.lzb.map;

import java.util.*;

public class TestWeakHashMap {

	public static final String AAA_VALUE = "aaa_value";
	public static final String AAA_KEY = "aaa_key";
	
	public static void main0(String[] args) throws Exception {
		String a = new String("a");
		String b = new String("b");
		Map weakmap = new WeakHashMap();
		Map map = new HashMap();
		map.put(a, "aaa");
		map.put(b, "bbb");

		weakmap.put(a, "aaa");
		weakmap.put(b, "bbb");

		map.remove(a);

		a = null;

		System.gc();
		Iterator i = map.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry en = (Map.Entry) i.next();
			System.out.println("map:" + en.getKey() + ":" + en.getValue());
		}

		Iterator j = weakmap.entrySet().iterator();
		while (j.hasNext()) {
			Map.Entry en = (Map.Entry) j.next();
			System.out.println("weakmap:" + en.getKey() + ":" + en.getValue());

		}
	}  
	
	
	public static void main(String[] args) throws InterruptedException {
		Work worker = null;
		for(int i=0; i<1000000; i++) {
			//最终通过 jmap 导出内存快照，发现并没有大量的HashMap存在，表示当线程消亡，Map变量也会被回收
//			worker = new Work();
//			Thread thread = new Thread(worker, "test-worker");
//			thread.start();
//			//main 线程等到 thread 执行完再执行
//			thread.join();
//			System.gc();
			
		}
		System.out.println("end");
	}
	
	static class Work implements Runnable {
		public ThreadLocal<Map<String, String>> t = new ThreadLocal<Map<String, String>>();
		public Work() {
		}
		
		@Override
		public void run() {
			Map<String, String> hashMap = new HashMap<String, String>();
			//Map<String, String> weakHashMap = new WeakHashMap<String, String>();
			hashMap.put(AAA_KEY, AAA_VALUE);
			t.set(hashMap);
		}
	} 
}
