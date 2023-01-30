package com.byhot.dblock;

import com.byhot.dblock.service.LockService;
import com.byhot.dblock.service.impl.LockServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DblockConfiguration {

    @Bean
    public LockService lockService() {
        return new LockServiceImpl();
    }

}
