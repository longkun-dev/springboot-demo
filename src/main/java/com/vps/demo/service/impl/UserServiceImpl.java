package com.vps.demo.service.impl;

import com.vps.demo.po.UserPo;
import com.vps.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author zhulongkun20@163.com
 * @since 2021/6/13 7:52 PM
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<UserPo> listUsers(int count) {
        if (count <= 0) {
            return Collections.emptyList();
        }

        if (count > 20) {
            count = 20;
        }

        List<UserPo> userPoList = new ArrayList<>(count);
        UserPo userPo = null;
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            userPo = new UserPo();
            userPo.setNo("U00-" + i);
            userPo.setName("U-name" + i);
            userPo.setLevel(i % 5 == 0 ? "高级用户" : "初级用户");
            userPo.setPoint(random.nextInt(1000));
            userPoList.add(userPo);
        }
        return userPoList;
    }
}
