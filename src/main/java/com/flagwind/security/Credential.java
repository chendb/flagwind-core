package com.flagwind.security;

import java.util.Date;

/**
 * 表示用户的安全凭证
 */
public interface Credential {
        /**
         * 获取安全凭证编号。
         * 
         * @return string
         */
        String getCredentialId();
        
        /**
         * 获取安全凭证对应的用户编号。
         * 
         * @return string
         */
        String geUuserId() ;

        /**
         * 获取安全凭证的过期时间。
         * 
         * @return Date
         */
        Date getExpires();
}