package com.flagwind.services;

import java.util.ArrayList;

/**
 * 断言集合
 * 
 * @author chendb
 * @date 2016年12月9日 上午9:31:51
 */
public class PredicationCollection extends ArrayList<Predication> implements Predication {

    private static final long serialVersionUID = 6753896880041271740L;

    // {{ 私有变量

    private PredicationCombination combination;

    // }}

    // {{ 属性

    public PredicationCombination getCombination() {
        return combination;
    }

    public void setCombination(PredicationCombination combination) {
        this.combination = combination;
    }

    // }}

    // {{ 构造函数

    /**
     * 构造函数
     */
    public PredicationCollection() {
        this(PredicationCombination.Or);
    }

    /**
     * 构造函数
     * 
     * @param combine 组合方式
     */
    public PredicationCollection(PredicationCombination combination) {
        this.combination = combination;
    }

    // }}

    // {{ 重载方法

    @Override
    public boolean predicate(Object parameter) {

        if (this.size() < 1) {
            return true;
        }

        for (Predication p : this) {
            if (p.predicate(parameter)) {
                if (combination == PredicationCombination.Or) {
                    return true;
                }
            } else {
                if (combination == PredicationCombination.And) {
                    return false;
                }
            }
        }
        return combination == PredicationCombination.Or ? false : true;
    }

    // }}
}
