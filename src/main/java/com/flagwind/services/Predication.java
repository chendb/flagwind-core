package com.flagwind.services;

/**
 * 表示条件判断的接口。
 * 
 * @author chendb hbchendb1985@hotmail
 * @date 2015年9月2日 下午1:25:22
 */
public interface Predication {

    /**
     * 判断实现
     * 
     * @param parameter 判断需要参数
     * @return 是否成功
     * @author chendb
     * @date 2016年12月9日 上午10:47:41
     */
    boolean predicate(Object parameter);

}
