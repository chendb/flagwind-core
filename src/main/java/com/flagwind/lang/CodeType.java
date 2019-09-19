package com.flagwind.lang;

/**
 * 定义的一个基本类型接口，继续该接口的类型会被认为是基础类型
 */
public interface CodeType {
    /**
     * 属性值
     * @return 属性值
     * @author chendb
     * @date 2016年12月8日 下午11:44:20
     */
    String getValue();

    /**
     * 属性描述信息
     * @return 描述信息
     * @author chendb
     * @date 2016年12月8日 下午11:44:42
     */
    String getDescription();
}
