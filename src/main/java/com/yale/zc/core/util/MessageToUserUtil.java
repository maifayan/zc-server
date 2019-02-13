package com.yale.zc.core.util;

import com.yale.zc.system.bean.SysMessageToUser;

import java.util.Date;
import java.util.UUID;

/**
 * Created by WQHJD on 2017/9/27.
 */
public class MessageToUserUtil {
    public static SysMessageToUser saveMessage(String userId,int status,String content,Date date){
        SysMessageToUser sysMessageToUser = new SysMessageToUser();
        sysMessageToUser.setId(UUID.randomUUID().toString());
        sysMessageToUser.setUserId(userId);
        sysMessageToUser.setStatus(status);
        sysMessageToUser.setContent(content);
        sysMessageToUser.setCreatedAt(date);
        sysMessageToUser.setUpdatedAt(date);
        return sysMessageToUser;
    }
}
