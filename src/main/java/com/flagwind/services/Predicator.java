package com.flagwind.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Predicator extends ArrayList<Predication> {

    private static final long serialVersionUID = -5635017603361141589L;

    /**
     * 获取为真的判断集合
     * 
     * @param parameter 判断参数
     * @return 为真的判断集合
     * @author chendb
     * @date 2016年12月9日 上午11:11:08
     */
    public List<Predication> getSuccesses(Object parameter) {
        List<Predication> predications = new ArrayList<Predication>();

        if (this.size() == 0) {
            return predications;
        }

        predications = this.stream().filter(p -> p.predicate(parameter)).collect(Collectors.toList());

        return predications;

    }

    /**
     * 获取为假的判断集合
     * 
     * @param parameter 判断需要的参数
     * @return 判断结果
     * @author chendb
     * @date 2016年12月9日 上午10:49:55
     */
    public List<Predication> getFailures(Object parameter) {
        List<Predication> predications = new ArrayList<Predication>();

        if (this.size() == 0) {
            return predications;
        }

        predications = this.stream().filter(p -> !p.predicate(parameter)).collect(Collectors.toList());

        EventHandler ll = new EventHandler();
        ll.registerEvent(this, g -> g.Name = "");
        // this.stream().filter(p->!p.Predicate(parameter)).forEach()
        Lamdba<Person> pl = new Lamdba<Person>();
        pl.test(g -> g.Name = "0");
        return predications;

    }

    /**
     * 操作示例 处理类
     * 
     * @author chendb
     * @date 2016年12月9日 上午10:52:46
     */
    public class EventHandler {
        public <T, V extends Person> boolean registerEvent(T object, Consumer<? super V> action) {

            return true;
        }
    }

    /**
     * 自定义lamba表达式
     * 
     * @author chendb
     * @date 2016年12月9日 上午10:53:16
     * @param <T>
     */
    public class Lamdba<T> {
        public void test(Consumer<? super T> action) {

        }
    }

    /**
     * 测试对象
     * 
     * @author chendb
     * @date 2016年12月9日 上午10:53:45
     */
    public class Person {
        /**
         * 名称
         */
        public String Name;

        /**
         * 值
         */
        public String Value;
    }

}
