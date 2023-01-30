package com.byhot.dblock.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Lock {
    /**
     * 锁的标示，以订单为例，可以锁订单id
     */
    private String tag;

    /**
     * 过期时间
     */
    private Date expirationTime;


}