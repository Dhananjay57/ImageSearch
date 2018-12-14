package com.imagesearch.data.cache;

/**
 * Created by Varun on 28,July,2018
 */

public interface Cache<K, V> {

    void put(K key, V value);

    V get(K key);

    V remove(K key);

    void clear();
}