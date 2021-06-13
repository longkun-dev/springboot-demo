package com.vps.demo.service;

import com.vps.demo.po.UserPo;

import java.util.List;

/**
 * @author zhulongkun20@163.com
 * @since 2021/6/13 7:50 PM
 */
public interface UserService {

    /**
     * 获取用户列表
     *
     * @param count 获取用户的数量
     * @return 指定数量的用户集合
     */
    List<UserPo> listUsers(int count);
}
