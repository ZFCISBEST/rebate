package com.help.rebate.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class LoginUserInfo {

    public static Map<String, LoginUserInfo> username2InfoMap = new HashMap<String, LoginUserInfo>();

    static {
        username2InfoMap.put("admin", createDefault("admin", "admin", "admin"));
        username2InfoMap.put("yanglaojin", createDefault("yanglaojin", "admin", "admin"));
    }

    /**
     * type CurrentUser = {
     *     name?: string;
     *     avatar?: string;
     *     userid?: string;
     *     email?: string;
     *     signature?: string;
     *     title?: string;
     *     group?: string;
     *     tags?: { key?: string; label?: string }[];
     *     notifyCount?: number;
     *     unreadCount?: number;
     *     country?: string;
     *     access?: string;
     *     geographic?: {
     *       province?: { label?: string; key?: string };
     *       city?: { label?: string; key?: string };
     *     };
     *     address?: string;
     *     phone?: string;
     *   };
     */
    String username;
    String avatar;
    String userId;
    String email;
    String signature;
    String title;
    String group;
    List<Tag> tags;
    String notifyCount;
    String unreadCount;
    String country;
    String access;
    String address;
    String phone;

    public static class Tag {
        String key;
        String label;
    }

    /**
     * 创建一个默认的
     * @return
     */
    public static LoginUserInfo createDefault(String userName, String access, String title) {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setUsername(userName);
        loginUserInfo.setAvatar("https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png");
        loginUserInfo.setAccess(access);
        loginUserInfo.setTitle(title);
        return loginUserInfo;
    }

    public static LoginUserInfo getByUserName(String username) {
        return username2InfoMap.getOrDefault(username, createDefault(username, "visitor", "visitor"));
    }
}
