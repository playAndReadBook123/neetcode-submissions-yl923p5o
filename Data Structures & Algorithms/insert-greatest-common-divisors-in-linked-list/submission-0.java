/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode insertGreatestCommonDivisors(ListNode head) {
        
        if(head == null){
            return null;
        }

        ListNode cur = head;
        while(cur.next != null){
            ListNode tmp = cur.next;

            int n1 = cur.val;
            int n2 = cur.next.val;
            int val = gcd(n1, n2);

            ListNode node = new ListNode(val);
            cur.next = node;
            node.next = tmp;
            cur = tmp;
        }

        return head;
    }


    private int gcd(int a, int b){

        while(b > 0){
            int tmp = b;
            b = a % b;
            a = tmp;
        }

        return a;
    }
}