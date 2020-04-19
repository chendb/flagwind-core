package com.flagwind.lang;

/**
 * 定义的一个基本类型接口，继续该接口的类型会被认为是基础类型
 */
public interface CodeType {
    /**
     * 属性值
     * @return 属性值
     */
    String getValue();

    /**
     * 属性描述信息
     * @return 描述信息
     */
    String getText();
}
