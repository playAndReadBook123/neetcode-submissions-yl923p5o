class LFUCache {
    int cap;
    Map<Integer, Node> nodeMap = new HashMap<>();
    Map<Integer, DLinkedList> listMap = new HashMap<>();
    int lfuCnt = 1;

    public LFUCache(int capacity) {
        this.cap = capacity;
    }

    void move(Node node){
        int freq = node.freq;
        DLinkedList list = listMap.get(freq);
        list.remove(node);

        if(freq == lfuCnt && list.size == 0){
            lfuCnt++;
        }
        node.freq = ++freq;
        listMap.putIfAbsent(node.freq, new DLinkedList());
        listMap.get(node.freq).add(node);
    }

    public int get(int key) {
        if(!nodeMap.containsKey(key)){
            return -1;
        }

        Node node = nodeMap.get(key);
        move(node);
        return node.val;
    }

    public void put(int key, int value) {

        if(cap == 0){
            return;
        }

        if(nodeMap.containsKey(key)){
            Node node = nodeMap.get(key);
            node.val = value;
            move(node);
            return;
        }

        if(cap == nodeMap.size()){
            DLinkedList list = listMap.get(lfuCnt);
            Node least = list.removeLeast();
            nodeMap.remove(least.key);
        }

        if(!nodeMap.containsKey(key)){
            Node node = new Node();
            node.val = value;
            node.key = key;
            node.freq = 1;
            lfuCnt = 1;
            listMap.putIfAbsent(1, new DLinkedList());
            listMap.get(1).add(node);
            nodeMap.put(key, node);
        }
    }
}

class Node{
    Node pre;
    Node next;
    int key;
    int freq = 1;
    int val;
}

class DLinkedList{
    Node head;
    Node tail;
    int size = 0;

    DLinkedList(){
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.pre = head;
    }

    Node add(Node node){
        Node tmp = head.next;
        head.next = node;
        node.pre = head;
        tmp.pre = node;
        node.next = tmp;
        size++;
        return node;
    }

    Node remove(Node node){
        Node tmpP = node.pre;
        Node tmpN = node.next;
        tmpP.next = tmpN;
        tmpN.pre = tmpP;
        node.next = null;
        node.pre = null;
        size--;
        return node;
    }

    Node removeLeast(){
        Node removeNode = tail.pre;
        return remove(removeNode);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */