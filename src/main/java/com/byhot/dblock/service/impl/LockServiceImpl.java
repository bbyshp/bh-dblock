package com.byhot.dblock.service.impl;

import com.byhot.dblock.entity.Lock;
import com.byhot.dblock.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class LockServiceImpl implements LockService {

    private final Integer DEFAULT_EXPIRED_SECONDS = 10;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean tryLock(String tag, Integer expiredSeconds) {
        if (StringUtils.isEmpty(tag)) {
            throw new NullPointerException();
        }
        List<Lock> locks = jdbcTemplate.query("select * from lock_info where tag = ?", new Object[]{tag}, new BeanPropertyRowMapper<>(Lock.class));
        if (locks == null || locks.size() == 0) {
            jdbcTemplate.update("INSERT INTO `lock_info`(`tag`,`expiration_time`) VALUES(?,?)", tag, this.addSeconds(expiredSeconds));
            return true;
        } else {
            Lock lock = locks.get(0);
            if (lock.getExpirationTime().before(new Date())) {
                jdbcTemplate.update("UPDATE `lock_info` SET `expiration_time`=? WHERE tag=?", this.addSeconds(expiredSeconds), tag);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void unlock(String tag) {
        if (StringUtils.isEmpty(tag)) {
            throw new NullPointerException();
        }
        jdbcTemplate.update("DELETE FROM `lock_info` WHERE tag=?", tag);
    }

    private Date addSeconds(Integer seconds) {
        if (Objects.isNull(seconds)) {
            seconds = DEFAULT_EXPIRED_SECONDS;
        }
        Calendar calendar = Calendar.getInstance();
        if (seconds.intValue() < 0) {
            calendar.set(9999, 12, 31);
        } else {
            calendar.add(Calendar.SECOND, seconds);
        }
        return calendar.getTime();
    }
}