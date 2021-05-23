package com.htech.socketio_demo.task;


import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;


/**
 * @author Ron
 * @date 2020/12/15 下午 12:06
 */
@Slf4j
@Component
@QuartzDataSource
@RequiredArgsConstructor
public class SystemScheduleTask {

    private static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final SocketIOServer socketIoServer;

    @Qualifier("testNamespace")
    private final SocketIONamespace testNamespace;

    /**
     * 10秒钟印一次
     */
    @Scheduled(fixedRate = 1000)
    public void tenSecondJob() {
        log.debug(testNamespace.getAllClients().size() + "");
        testNamespace.getBroadcastOperations().sendEvent("toBrowser", "tset");
        socketIoServer.getBroadcastOperations().sendEvent("toBrowser", "all");
        testNamespace.getBroadcastOperations().sendEvent("toServer", "tset");
        socketIoServer.getBroadcastOperations().sendEvent("toServer", "all");

        System.out.println(testNamespace.getName() + " = " + socketIoServer.getNamespace("test").getAllClients().size() + "");
        System.out.println("socketIoServer = " + socketIoServer.getAllClients().size() + "");
        System.out.println("namespace1 = " + socketIoServer.getNamespace("test") + " namespace2 = " + testNamespace);
    }

}
