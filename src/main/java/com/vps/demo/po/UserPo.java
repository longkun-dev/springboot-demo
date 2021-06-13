package com.vps.demo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author zhulongkun20@163.com
 * @since 2021/6/13 7:45 PM
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserPo {

    /**
     * 顾客编号
     */
    private String no;

    /**
     * 姓名
     */
    private String name;

    /**
     * 等级
     */
    private String level;

    /**
     * 积分
     */
    private Integer point;
}
