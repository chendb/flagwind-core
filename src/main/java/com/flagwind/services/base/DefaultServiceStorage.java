package com.flagwind.services.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.flagwind.services.ServiceEntry;
import com.flagwind.services.ServiceProvider;

import org.apache.commons.lang3.StringUtils;

/**
 * author：chendb
 */
public class DefaultServiceStorage extends ServiceStorageBase implements Collection<ServiceEntry> {

    // region 成员字段

    private List<ServiceEntry> entries;
    private ConcurrentMap<String, ServiceEntry> namedEntries;

    // endregion

    // region 构造函数

    public DefaultServiceStorage(ServiceProvider provider) {
        super(provider);
        this.entries = new ArrayList<>();
        namedEntries = new ConcurrentHashMap<String, ServiceEntry>();
    }
    // endregion

    // region 公共属性
    @Override
    public int count() {
        return entries.size();
    }
    // endregion

    // region 公共方法


    @Override
    public boolean add(ServiceEntry entry)
    {

        if(StringUtils.isNotEmpty(entry.name())) {
            namedEntries.put(entry.name(), entry);
        }

        entries.add(entry);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends ServiceEntry> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear()
    {
        namedEntries.clear();
        entries.clear();
    }

    @Override
    public ServiceEntry remove(String name)
    {
        if(StringUtils.isEmpty(name)) {
            return null;
        }

        ServiceEntry entry = namedEntries.remove(name);

        entries.remove(entry);

        return entry;
    }

    @Override
    public ServiceEntry get(String name)
    {
        if(StringUtils.isEmpty(name)) {
            return null;
        }

        ServiceEntry namedEntry=namedEntries.get(name);

        //首先从命名项的字典中查找指定名称的服务项
        if(namedEntry!=null) {

            return namedEntry;
        }

        //调用基类的查找逻辑
        return super.get(name);
    }


    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return entries.contains(o);
    }

    @Override
    public Iterator<ServiceEntry> iterator() {
        return entries.iterator();
    }

    @Override
    public Object[] toArray() {
        return entries.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return Arrays.copyOf(a, a.length);
    }


    // endregion

}
