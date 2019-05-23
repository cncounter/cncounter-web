package com.cncounter.test.util.hash;

import java.util.*;

/*
 哈希表通过一个特定的哈希函数将 key 值转换为一个固定的地址，然后将对应的　value 放到这个位置，
 如果发生哈希碰撞就在这个位置拉出一个链表，由于哈希函数的离散特性，所以经过哈希函数处理后的 key 将失去原有的顺序，
 所以哈希结构的索引无法满足范围查询，只适合等值查询的情况例如一些缓存的场景。
 */
public class CNCHashMap<K, V> implements Map<K, V> {

    // 内部节点
    static class Node<K, V> implements Map.Entry<K, V> {
        // hash值由外层进行计算,
        private final int hash;
        // 一般指定为 final; 所以 Map.Entry 中没有 setKey() 方法
        private final K key;
        private V value;
        // 链表! 链表!
        private Node<K, V> next; // 不能有 get-set方法, 否则泄露到外面去了

        public Node(int hash, K key, V value, Node<K, V> next) {
            // 执行完构造函数之后, 就不能再赋值  final 字段了。
            // final 字段也只能赋值一次； 编译器负责语义层面的检查。
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public String toString() {
            return "key=" + value;
        }
    }

    private static final int DEFAULT_INITIAL_CAPACITY = 16; // 默认 bucket 初始化容量
    private static final float DEFAULT_LOAD_FACTOR = 0.75f; // 默认加载因子

    //
    private Node<K, V>[] bucket; // hash桶; 即 table
    //
    private int size; // 内有的元素个数
    private int threshold; // size 临界值; 元素个数超过此值就扩容
    private float loadFactor; // 加载因子

    public CNCHashMap() {
        //
        this.size = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.threshold = (int) (this.loadFactor * DEFAULT_INITIAL_CAPACITY);
    }

    @Override
    public V put(K key, V value) {
        // 确保bucket的大小合理
        makesureBucketSize();
        // 此槽位有节点存在; 需要判断是否存在 key 相等的节点
        // 获取同样key的Node
        Node<K, V> sameKeyNode = this.getNode(key);
        if (null != sameKeyNode) {
            // 替换 value
            V oldValue = sameKeyNode.getValue();
            sameKeyNode.setValue(value);
            return oldValue; // 只有此处需要返回
        }
        //
        // 计算hash
        int hash = hash(key);
        // 新建 Node
        Node<K, V> node = new Node<K, V>(hash, key, value, null);
        // 安排
        placeIntoBucket(node);
        this.size++;
        //
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> otherMap) {
        if (null == otherMap || otherMap.isEmpty()) {
            return;
        }
        // 简单点, 不考虑效率, 遍历后依次 put 进去即可
        Set<? extends K> keySet = otherMap.keySet();
        for (K key : keySet) {
            V value = otherMap.get(key);
            this.put(key, value);
        }
    }

    @Override
    public V get(Object key) {
        Node<K, V> sameKeyNode = this.getNode((K) key);
        if (null != sameKeyNode) {
            return sameKeyNode.getValue();
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        Node<K, V> sameKeyNode = this.getNode((K) key);
        if (null != sameKeyNode) {
            return true;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        // 遍历 bucket 和其中的链表
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return 0 == this.size();
    }

    @Override
    public V remove(Object key) {
        // 简单点, 找到对应的 node, 处理好前后节点关系即可。
        Node<K, V> prevNode = null;
        Node<K, V> sameKeyNode = null;
        // 计算hash
        int hash = hash(key);
        //  定位 index
        int index = hashToIndex(hash, this.bucket.length);
        // 之前的Node
        Node<K, V> node = this.bucket[index];
        while (null != node) {
            //
            K theKey = node.getKey();
            if (key == theKey) { // 直接相等; 包括 null
                sameKeyNode = node;
            } else if (null != key && key.equals(theKey)) { // key 相等
                sameKeyNode = node;
            }
            if (null != sameKeyNode) {
                break; // 找到了
            }
            prevNode = node; // 没找到
            // 遍历链表
            node = node.next;
        }
        if (null == sameKeyNode) {
            return null; // 没找到
        }
        // 有前节点
        if (null != prevNode && prevNode != sameKeyNode) {
            // 从链表中摘除
            prevNode.next = sameKeyNode.next;
        } else {
            // 没有前节点; 直接将槽位设置为 next
            this.bucket[index] = sameKeyNode.next;
        }
        // 控制size
        this.size--;

        return sameKeyNode.getValue();
    }

    @Override
    public void clear() {
        if (null == bucket) {
            return;
        }
        for (int i = 0; i < bucket.length; i++) {
            bucket[i] = null;
        }
        this.size = 0;
    }

    @Override
    public Set<K> keySet() {
        // 按道理可维护一个不可外部修改的keyset，有变更就维护
        // 简单实现，直接使用 HashSet
        Set<K> theKeySet = new HashSet<K>();

        // 简单遍历
        Set<Entry<K, V>> theEntrySet = this.entrySet();
        for (Entry<K, V> entry : theEntrySet) {
            if (null == entry) {
                continue;
            }
            theKeySet.add(entry.getKey());
        }
        //
        return theKeySet;
    }

    @Override
    public Collection<V> values() {
        // 简单实现，直接使用 ArrayList
        ArrayList<V> valueList = new ArrayList<V>(this.size());
        // 简单遍历
        Set<Entry<K, V>> theEntrySet = this.entrySet();
        for (Entry<K, V> entry : theEntrySet) {
            if (null == entry) {
                continue;
            }
            valueList.add(entry.getValue());
        }
        //
        return valueList;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        //
        Set<Entry<K, V>> theEntrySet = new HashSet<Entry<K, V>>();
        if (null == this.bucket) {
            return theEntrySet;
        }
        //
        int bucketLength = this.bucket.length;

        // 遍历并重新放置旧的数据
        for (int i = 0; i < bucketLength; i++) {
            //
            Node<K, V> node = this.bucket[i];
            // 整个链表遍历
            while (null != node) {
                //
                theEntrySet.add(node);
                // 遍历链表
                node = node.next;
            }

        }
        //
        return theEntrySet;
    }

    // 确保bucket的大小合理
    private void makesureBucketSize() {
        // bucket 为空的情况
        if (null == this.bucket || 0 == this.bucket.length) {
            this.resize();
            return;
        }
        // 成员数量大于临界值
        if (this.size >= this.threshold) {
            this.resize();
            return;
        }
    }

    // 调整大小
    private Node<K, V>[] resize() {
        // 之前的 bucket
        Node<K, V>[] oldBucket = this.bucket;
        // 之前的 bucket 大小
        int oldBucketLength = 0;
        if (null != oldBucket) {
            oldBucketLength = oldBucket.length;
        }
        // 之前的临界值
        int oldThreshold = threshold;
        //
        // 计算新的 bucket 大小
        int newBucketLength = 0;
        if (0 == oldBucketLength) {
            newBucketLength = DEFAULT_INITIAL_CAPACITY;
        } else if (oldBucketLength > 0) {
            newBucketLength = oldBucketLength * 2;
        }
        int newThreshold = (int) (newBucketLength * this.loadFactor);
        //
        this.threshold = newThreshold;
        //
        // 创建新的 bucket
        Node<K, V>[] newBucket = (Node<K, V>[]) new Node[newBucketLength];
        this.bucket = newBucket;
        // 如果没有旧的内容，直接返回新创建的空桶
        if (null == oldBucket) {
            return this.bucket;
        }
        // 遍历并重新放置旧的数据
        for (int i = 0; i < oldBucketLength; i++) {
            //
            Node<K, V> node = oldBucket[i];
            // 安排对应的元素
            placeIntoBucket(node);
        }
        //
        return this.bucket;

    }

    private Node<K, V> getNode(K key) {
        // 计算hash
        int hash = hash(key);
        //  定位 index
        int index = hashToIndex(hash, this.bucket.length);
        // 之前的Node
        Node<K, V> node = this.bucket[index];
        while (null != node) {
            //
            K theKey = node.getKey();
            if (key == theKey) { // 直接相等; 包括 null
                return node;
            }
            if (null != key && key.equals(theKey)) { // key 相等
                return node;
            }
            // 遍历链表
            node = node.next;
        }
        return null;
    }

    // 安排对应的元素
    private void placeIntoBucket(Node<K, V> node) {
        //
        if (null == node || null == this.bucket) {
            return; // 不处理null的情况
        }
        // 先把 next 取出来暂存
        Node<K, V> next = node.next;
        node.next = null; // 打断链表关系
        // 计算 bucket的index
        int index = hashToIndex(node.hash, this.bucket.length);
        // 判断此索引位置有没有元素
        Node<K, V> slotNode = this.bucket[index];
        if (null != slotNode) {
            node.next = slotNode; // hash冲突; 存为链表模式
        }
        this.bucket[index] = node; // 将node放到index位置
        // 处理旧的链表不需要考虑节点重复的问题

        // TIPS: 递归有个问题: 如果hash冲突严重, 可能导致 stackoverflow; 可改用循环来实现。
        // 安排 next 节点; 如果有的话
        placeIntoBucket(next);
    }

    // 将 hash 映射到 bucket的index位置
    private static int hashToIndex(int hash, int bucketLength) {
        return hash % bucketLength; // 取模运算; 位运算也可以
    }

    /**
     * 先获取 key.hashCode(), 然后进行二次hash: 将高位 hash 值扩散到低位(XORs)。目的是为了降低 hash 冲突;
     * 因为hash桶的长度一般是2的幂次方，如果某些类的hash值只在某几个bit上有区别，那么hash冲突可能会很严重。
     * （已知的例子中是Float作为key时，会在很小的hash桶上保存连续的整数。）
     * 因此应用了一种变换，将高位bit扩散到低位。这综合权衡了速度，实用性和质量。
     * 许多常见类的哈希分布已经很合理了（也就不会受益于此种扩散），
     * 在 JDK8中使用 TreeNode 来处理大量hash冲突的情况，所以只需要进行简单位移动和异或运算(XOR)，以降低性能损失，
     * 以及hash桶不可能太大，所以高位bit一般不会有什么影响; 想一想: 二进制中高位的1,对一个2的幂次方取模。
     */
    private static int hash(Object key) {
        int theHash = 0;
        if (null != key) {
            return theHash; // null 值
        }
        theHash = key.hashCode();
        // ^ 异或;
        // >>> 无符号右移;
        theHash = theHash ^ (theHash >>> 16);
        //
        return theHash;
    }

    @Override
    public String toString() {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if (! i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
            Entry<K,V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key   == this ? "(this Map)" : key);
            sb.append('=');
            sb.append(value == this ? "(this Map)" : value);
            if (! i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }
}
