package com.lzb.mixed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 顺序链表转奇偶链表<br/>
 * Created on : 2023-11-17 14:40
 * @author lizebin
 */
public class OddEvenExchange {

    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    /*@Test
    @DisplayName("添加链表")*/
    void should_add_node() {

        int rootVal = 1;
        int length = 9;
        ListNode root = createList(rootVal, length);
        int except = 1;
        // assertThat(root.val).isEqualTo(rootVal);
        while (Objects.nonNull(root.next)) {
            // assertThat(root.val).isEqualTo(except++);
            root = root.next;
        }

    }

    /*@Test
    @DisplayName("奇偶遍历")*/
    void should_odd_even_exchange() {
        ListNode root = createList(1, 9);

        //奇偶替换
        oddEvenExchange(root);

        List<Integer> excepts = Arrays.asList(1, 3, 5, 7, 9, 2, 4, 6, 8);
        List<Integer> actual = new ArrayList<>();
        while (Objects.nonNull(root.next)) {
            actual.add(root.val);
            root = root.next;
        }

        // assertThat(actual).isEqualTo(excepts);
    }

    private static ListNode createList(int rootVal, int length) {
        ListNode root = new ListNode(rootVal);
        ListNode current = root;
        for (int i = 0; i < length; i++) {
            current.next = new ListNode(current.val + 1);
            current = current.next;
        }
        return root;
    }

    private void oddEvenExchange(ListNode root) {
        ListNode odd = root;
        ListNode event = root.next;
        ListNode eventHead = event;
        while(Objects.nonNull(odd.next) && Objects.nonNull(event.next)) {
            odd.next = event.next;
            odd = odd.next;
            event.next = odd.next;
            event = event.next;
        }
        odd.next = eventHead;
    }

}
