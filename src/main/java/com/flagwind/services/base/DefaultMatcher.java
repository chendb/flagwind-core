package com.flagwind.services.base;

import com.flagwind.services.Matchable;
import com.flagwind.services.Matcher;

/**
 * 服务提供者工厂
 * 
 * @author chendb
 * @date 2015年10月21日 上午10:55:05
 */
public class DefaultMatcher implements Matcher {

    public static final DefaultMatcher Default = new DefaultMatcher();

    public boolean match(Object target, Object parameter) {
        if (target == null)
            return false;

        if (target instanceof Matchable) {
            return ((Matchable) target).isMatch(parameter);
        }

        // var attribute = (MatcherAttribute)Attribute.GetCustomAttribute(target.GetType(), typeof(MatcherAttribute), true);

        // if(attribute != null && attribute.Matcher != null)
        //     return attribute.Matcher.Match(target, parameter);

        //注意：默认返回必须是真
        return true;
    }

}
