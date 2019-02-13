package com.yale.zc.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

    public static String REDIS_USER_SESSION_KEY;

    public static String REDIS_SYS_USER_SESSION_KEY;

    public static Integer REDIS_SESSION_EXPIRE;

    public static String REDIS_SMS_PREFIX;

    public static Integer REDIS_SMS_EXPIRE;

    public static String REDIS_SMS_VALIDATE;

    public static String REDIS_SMS_NOTIFY_VALIDATE;

    public static String REDIS_SMS_PROTECTION_PREFIX;

    public static Integer REDIS_SMS_PROTECTION_EXPIRE;

    public String getRedisUserSessionKey() {
        return REDIS_USER_SESSION_KEY;
    }

    @Value("${redis.user-session-key}")
    public void setRedisUserSessionKey(String redisUserSessionKey) {
        REDIS_USER_SESSION_KEY = redisUserSessionKey;
    }

    public String getRedisSysUserSessionKey() {
        return REDIS_SYS_USER_SESSION_KEY;
    }

    @Value("${redis.sys-user-session-key}")
    public void setRedisSysUserSessionKey(String redisSysUserSessionKey) {
        REDIS_SYS_USER_SESSION_KEY = redisSysUserSessionKey;
    }

    public Integer getRedisSessionExpire() {
        return REDIS_SESSION_EXPIRE;
    }

    @Value("${redis.session-expire}")
    public void setRedisSessionExpire(Integer redisSessionExpire) {
        REDIS_SESSION_EXPIRE = redisSessionExpire;
    }

    public String getRedisSmsPrefix() {
        return REDIS_SMS_PREFIX;
    }

    @Value("${redis.sms-prefix}")
    public void setRedisSmsPrefix(String redisSmsPrefix) {
        REDIS_SMS_PREFIX = redisSmsPrefix;
    }

    public Integer getRedisSmsExpire() {
        return REDIS_SMS_EXPIRE;
    }

    @Value("${redis.sms-expire}")
    public void setRedisSmsExpire(Integer redisSmsExpire) {
        REDIS_SMS_EXPIRE = redisSmsExpire;
    }

    public String getRedisSmsValidate() {
        return REDIS_SMS_VALIDATE;
    }

    @Value("${redis.sms-validate}")
    public void setRedisSmsValidate(String redisSmsValidate) {
        REDIS_SMS_VALIDATE = redisSmsValidate;
    }


    public String getRedisSmsNotifyValidate() {
        return REDIS_SMS_NOTIFY_VALIDATE;
    }

    @Value("${redis.sms-notify-validate}")
    public void setRedisSmsNotifyValidate(String redisSmsNotifyValidate) {
        REDIS_SMS_NOTIFY_VALIDATE = redisSmsNotifyValidate;
    }



    public String getRedisSmsProtectionPrefix() {
        return REDIS_SMS_PROTECTION_PREFIX;
    }

    @Value("${redis.sms-protection-prefix}")
    public void setRedisSmsProtectionPrefix(String redisSmsProtectionPrefix) {
        REDIS_SMS_PROTECTION_PREFIX = redisSmsProtectionPrefix;
    }

    public Integer getRedisSmsProtectionExpire() {
        return REDIS_SMS_PROTECTION_EXPIRE;
    }

    @Value("${redis.sms-protection-expire}")
    public void setRedisSmsProtectionExpire(Integer redisSmsProtectionExpire) {
        REDIS_SMS_PROTECTION_EXPIRE = redisSmsProtectionExpire;
    }
}
