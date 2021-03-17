package com.lzb.queue.itf;

/**
 * 队列实现:先进先出，它只允许在表的前端进行删除操作，而在表的后端进行插入操作。
 * 就好比食堂打饭，新来（新插入）的排在队尾，打好饭（删除）的称为队头<br/>
 * Created on : 2020-12-08 22:58
 * @author lizebin
 */
public interface Queue<E> {

    /**
     * 队列有效个数
     * @return
     */
    public int size();

    /**
     * 队列是否为空
     * @return
     */
    public boolean isEmpty();

    /**
     * 插入队列尾
     *
     * @param e
     * @return
     */
    public boolean offer(E e);

    /**
     * 获取并移除队列头，如果队列为空返回空
     *
     * @return
     */
    public E poll();

    /**
     * 获取对列头，但是不移除
     *
     * @return
     */
    public E peek();

    /**
     * 是否满了
     * @return
     */
    public boolean isFull();

    /**
     * 显示队列
     */
    public void show();

}