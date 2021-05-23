package com.htech.socketio_demo.config;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * SpringBoot启动之后执行
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2017/7/31
 */
@Slf4j

@Component
@RequiredArgsConstructor
public class ServerRunner implements CommandLineRunner {

    private final SocketIOServer server;
    @Override
    public void run(String... args) {

        server.start();

        log.info("SocketIOServer 开始启动啦...");
    }
}
