package com.flagwind.security;

import java.util.Date;

/**
 * 表示用户的安全凭证
 */
public interface Credential {
        /**
         * 获取安全凭证编号。
         * @member
         * @returns string
         */
        String getCredentialId();
        
        /**
         * 获取安全凭证对应的用户编号。
         * @member
         * @returns string
         */
        String geUuserId() ;

        /**
         * 获取安全凭证的过期时间。
         * @member
         * @returns Date
         */
        Date getExpires();
}