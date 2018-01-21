package com.flagwind.commands;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class CommandOptionCollection implements Iterable<Entry<String, String>> {

    private HashMap<String, String> items;

    // #region 构造函数
    public CommandOptionCollection() {
        items = new HashMap<>();
    }

    public CommandOptionCollection(Collection<Entry<String, String>> its) {
        if (items == null) {
            throw new NullPointerException("items");
        }

        this.items = new HashMap<>();
        its.forEach(g -> items.put(g.getKey(), g.getValue()));
    }
    // #endregion

    public int size() {
        return this.items.size();
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public boolean contains(Object key) {
        return this.items.containsKey(key);
    }

    @Override
    public Iterator<Entry<String, String>> iterator() {
        return items.entrySet().iterator();
    }

    public boolean add(Entry<String, String> e) {
        items.put(e.getKey(), e.getValue());
        return true;
    }

    public boolean remove(Object key) {
        items.remove(key);
        return true;
    }

}