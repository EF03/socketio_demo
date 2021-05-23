package com.htech.socketio_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;



/**
 * @author zhangxuanrong
 */
@Configuration
@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
@EnableTransactionManagement
@PropertySource({"classpath:socketio.properties"})
public class SocketioDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocketioDemoApplication.class, args);
    }

}
