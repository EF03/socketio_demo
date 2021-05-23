package com.htech.socketio_demo.config;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author zhangxuanrong
 */
@Slf4j
@Configuration
public class SocketConfig {

    @Resource
    private SocketProperties socketProperties;

//    @Value("${socketio.port}")
//    private Integer port;
//
//    @Value("${socketio.workCount}")
//    private int workCount;
//
//    @Value("${socketio.allowCustomRequests}")
//    private boolean allowCustomRequests;
//
//    @Value("${socketio.upgradeTimeout}")
//    private int upgradeTimeout;
//
//    @Value("${socketio.pingTimeout}")
//    private int pingTimeout;
//
//    @Value("${socketio.pingInterval}")
//    private int pingInterval;
//
//    @Value("${socketio.maxFramePayloadLength}")
//    private int maxFramePayloadLength;
//
//    @Value("${socketio.maxHttpContentLength}")
//    private int maxHttpContentLength;

//    @Value("${redis.host}")
//    private String redisHost;
//
//    @Value("${redis.password}")
//    private String redisPassword;
//
//    @Value("${redis.port}")
//    private String redisPort;

//    @Bean
//    public PubSubStore pubSubStore() {
//        return socketIOServer().getConfiguration().getStoreFactory().pubSubStore();
//    }

    @Bean("socketIOServer")
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        com.corundumstudio.socketio.SocketConfig socketConfig = config.getSocketConfig();
        socketConfig.setReuseAddress(socketProperties.isReuseAddress());
        socketConfig.setTcpReceiveBufferSize(socketProperties.getSendBufferSize());
        socketConfig.setTcpSendBufferSize(socketProperties.getSendBufferSize());
        // Socket关闭时的延迟时间（单位：秒）
        //socketConfig.setSoLinger(1);
        // 測試 會導致效能不好
        socketConfig.setTcpKeepAlive(socketProperties.isTcpKeepAlive());
        socketConfig.setAcceptBackLog(socketProperties.getAcceptBackLog());

        config.setTransports(Transport.WEBSOCKET);
        config.setOrigin(":*:");
        config.setBossThreads(socketProperties.getBossThread());
        config.setWorkerThreads(socketProperties.getWorkThread());
        // 设置监听端口
        config.setPort(socketProperties.getSocketPort());
        // 协议升级超时时间（毫秒），默认10000。HTTP握手升级为ws协议超时时间
        config.setUpgradeTimeout(socketProperties.getUpgradeTimeout());
        // Ping消息间隔（毫秒），默认25000。客户端向服务器发送一条心跳消息间隔
        config.setPingInterval(socketProperties.getPingInterval());
        // Ping消息超时时间（毫秒），默认60000，这个时间间隔内没有接收到心跳消息就会发送超时事件
        config.setPingTimeout(socketProperties.getPingTimeout());
        // 允许客户请求
        config.setAllowCustomRequests(socketProperties.getAllowCustomRequests());
        // 设置HTTP交互最大内容长度
        config.setMaxHttpContentLength(socketProperties.getMaxHttpContentLength());
        // 设置最大每帧处理数据的长度，防止他人利用大数据来攻击服务器
        config.setMaxFramePayloadLength(socketProperties.getMaxFramePayloadLength());
        // linux 專用 效能好
        config.setUseLinuxNativeEpoll(socketProperties.isUseLinuxNativeEpoll());

        config.setAuthorizationListener((data) -> {
            System.out.println("成功連線" + data.toString());
            return true;
        });

//        Config redissonConfig = new Config();
//        String redisUrl = "redis://" + redisHost + ":" + redisPort;
//        redissonConfig.useSingleServer().setPassword(redisPassword).setAddress(redisUrl).setDatabase(1);
//        RedissonClient redisson = Redisson.create(redissonConfig);
//        RedissonStoreFactory redisStoreFactory = new RedissonStoreFactory(redisson);
//
//        config.setStoreFactory(redisStoreFactory);
        SocketIOServer socketIOServer = new SocketIOServer(config);
        SocketIONamespace testNamespace = getTestNamespace(socketIOServer);
        testNamespace.addConnectListener(client -> System.out.println("有新用户连接, SessionId: " + client.getSessionId()));

        return socketIOServer;
    }

    @Bean("testNamespace")
    public SocketIONamespace getTestNamespace(SocketIOServer socketIOServer) {
        return socketIOServer.addNamespace("test");
    }

    /**
     * 开启SocketIOServer注解支持
     *
     * @param socketServer
     * @return com.corundumstudio.socketio.annotation.SpringAnnotationScanner
     * @author wliduo[i@dolyw.com]
     * @date 2019/7/31 18:21
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}