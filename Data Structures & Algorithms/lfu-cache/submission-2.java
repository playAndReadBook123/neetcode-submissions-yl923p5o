class LFUCache {

    Map<Integer, DLinkedList> listMap;
    Map<Integer, ListNode> nodeMap;
    int capacity = 0;
    int lfuCount = 0;
    
    public LFUCache(int capacity){
        listMap = new HashMap<>();
        nodeMap = new HashMap<>();
        this.capacity = capacity;
        lfuCount = 1;
    }

    void setCount(ListNode node){
        int freq = node.freq;
        listMap.get(freq).remove(node);

        if(freq == lfuCount && listMap.get(freq).getSize() == 0){
            lfuCount++;
        }
        node.freq = ++freq;
        listMap.putIfAbsent(node.freq, new DLinkedList());
        listMap.get(node.freq).pushRight(node);
    }

    public int get(int key) {
        
        if(!nodeMap.containsKey(key)){
            return -1;
        }
        ListNode node = nodeMap.get(key);
        setCount(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        
        if(capacity == 0){
            return;
        }

        if(nodeMap.containsKey(key)){
            ListNode node = nodeMap.get(key);
            node.val = value;
            setCount(node);
            return;
        }

        if(nodeMap.size() == capacity){
            ListNode node = listMap.get(lfuCount).removeLeft();
            nodeMap.remove(node.key);
        }

        ListNode toAdd = new ListNode(key, value);
        lfuCount = 1;
        listMap.putIfAbsent(lfuCount, new DLinkedList());
        listMap.get(lfuCount).pushRight(toAdd);
        nodeMap.put(key, toAdd);
    }
}

class ListNode{

    int key;
    int val;
    int freq;
    ListNode next, prev;

    ListNode(int key, int val){
        this.key = key;
        this.val = val;
        this.freq = 1;
    }
}

class DLinkedList{
    private ListNode left, right;
    private int size;

    DLinkedList(){
        this.left = new ListNode(0, 0);
        this.right = new ListNode(0, 0);
        this.left.next = right;
        this.right.prev = left;
        this.size = 0;
    }

    int getSize(){
        return this.size;
    }

    void pushRight(ListNode node){
        ListNode tmp = this.right.prev;
        this.right.prev = node;
        node.next = this.right;
        node.prev = tmp;
        tmp.next = node;
        size++;
    }

    void remove(ListNode node){
        ListNode pre = node.prev, next = node.next;
        pre.next = next;
        next.prev = pre;
        node.next = null;
        node.prev = null;
        size--;
    }

    ListNode removeLeft(){
        ListNode removeNode = left.next;
        remove(removeNode);
        return removeNode;
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */