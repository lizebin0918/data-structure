package com.lzb.list;

import java.util.Iterator;
import java.util.Objects;


/**
	自己实现的LinkedList
**/
public class MyLinkedList<E> implements MyList<E>, Iterable<E> {
	
	private Node<E> header;

	public MyLinkedList() {
		
	}
	public boolean isEmpty() {
		if(this.size() == 0) {
			return true;
		}
		return false;
	};
	
	@SuppressWarnings("unchecked")
	public E get(int index) {
		checkIndex(index);
		Node<E> next = header;
		int i = 0;
		while(next.getNext() != null) {
			if(i == index) {
				break;
			}
			next = next.getNext();
			i++;
		}
		return (E)next.getNodeValue();
	};
	
	public void add(E object) {
		add(this.size(), object);
	};
	
	public void add(int index, E object){
		Node<E> next = null;
		checkIndex(index);
		if(index == 0) {
			header = new Node<E>();
			header.setNodeValue(object);
			return;
		}
		next = header;
		int i = 0;
		while(next.getNext() != null && i < index) {
			next = next.getNext();
			i++;
		}
		Node<E> node = new Node<E>(object, next.getNext());
		next.setNext(node);
	};
	
	@SuppressWarnings("unchecked")
	public E remove(int index) {
		checkIndex(index);
		Node<E> next = header;
		Node<E> oldNode = null;
		E oldNodeValue = null;
		if(index == 0) {
			oldNode = header;
			oldNodeValue = oldNode.getNodeValue();
			header = header.getNext();
			oldNode.setNodeValue(null);
			oldNode.setNext(null);
			oldNode = null;
			return oldNodeValue;
		}
		int i = 0;
		while(next.getNext() != null && i < index - 1) {
			next = next.getNext();
			i++;
		}
		oldNode = next.getNext();
		next.setNext(oldNode.getNext());
		oldNodeValue = oldNode.getNodeValue();
		oldNode.setNodeValue(null);
		oldNode.setNext(null);
		oldNode = null;
		return oldNodeValue;
	};
	
	public int size() {
		Node<E> next = header;
		int size = 0;
		while(next != null) {
			next = next.getNext();
			size++;
		}
		return size;
	};
	
	public void clear() {
		Node<E> next = header;
		while(next.getNext() != null) {
			Node<E> temp = next;
			next = next.getNext();
			temp.setNodeValue(null);
			temp.setNext(null);
			temp = null;
		}
		next.setNodeValue(null);
		next = null;
	};
	
	public void set(int index, E object) {
		Node<E> next = header;
		int i = 0;
		while(next.getNext() != null) {
			if(i == index) {
				next.setNodeValue(object);
				return;
			}
			next = next.getNext();
			i++;
		}
	};
	
	//静态内部类好处
	//static : Parent.Children c = new Parent.Children();
	//nonstatic : Parent.Children c = new Parent().new Children();
	static class Node<E> {
		private E nodeValue;
		private Node<E> next;
		
		public Node(E nodeValue, Node<E> next) {
			this.nodeValue = nodeValue;
			this.next = next;
		}
		public Node() {
			
		}
		public E getNodeValue() {
			return nodeValue;
		}
		public void setNodeValue(E nodeValue) {
			this.nodeValue = nodeValue;
		}
		public Node<E> getNext() {
			return next;
		}
		public void setNext(Node<E> next) {
			this.next = next;
		}
	}
	
	public void checkIndex(int index) {
		if(index < 0) {
			throw new IllegalArgumentException("Illegal Index: " + index);
		}
		
		if(this.size() < index) {
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);
		}
	}
	
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new Itr<E>();
	} 
	
	private class Itr<E> implements Iterator<E> {

		private int lastIndex = -1;
		private int cursor;
		@Override
		public boolean hasNext() {
			//内部类引用外部类方法
			return cursor != MyLinkedList.this.size();
		}

		@Override
		public E next() {
			lastIndex = cursor;
			E data = (E) MyLinkedList.this.get(cursor++);
			return data;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}

    public static void main(String[] args) {
        MyLinkedList<String> list = new MyLinkedList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");

        list.forEach(System.out::println);

        System.out.println(list.header.nodeValue);
    }

    public void oddEvenPrint() {

        Node<E> oddNode = header.next;
        Node<E> evenNode = oddNode != null ? oddNode.next : null;
        while (Objects.nonNull(oddNode) && Objects.nonNull(evenNode)) {
            Node<E> eNext = evenNode.next;
            oddNode.next = eNext;
            oddNode = eNext;
            evenNode = oddNode.next;
        }


    }

}
