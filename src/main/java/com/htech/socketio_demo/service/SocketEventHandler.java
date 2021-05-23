package com.htech.socketio_demo.service;


import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息事件处理器
 *
 * @author Ron
 * @version 1.0
 * @since 2018/1/19
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SocketEventHandler {

    /* 传送给各个房间讯息 */
    private final SocketIOServer socketIoServer;



    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     * 在外部可以获取此连接的所有websocket对象，并能对其触发消息发送功能，我们的定时发送核心功能的实现在与此变量
     * <p>
     * private static final CopyOnWriteArraySet<SocketIOClient> webSocketSet = new CopyOnWriteArraySet<>();
     */
//    public static Map<String, SocketIOClient> sessionWithClientMap = new ConcurrentHashMap<>();

    private static Map<String, UUID> playerIdWithCUUIDMap = new ConcurrentHashMap<>(1024);
    @Qualifier("testNamespace")
    private final SocketIONamespace testNamespace;

    /**
     * 添加connect事件，当客户端发起连接时调用
     **/
    @OnConnect
    public void onConnect(SocketIOClient client) {
        try {
            log.info("onConnect :sessionId:" + client.getSessionId() + "  clientAddr:" + client.getRemoteAddress());
//            HandshakeData handshakeData = client.getHandshakeData();
//            String playerId = handshakeData.getSingleUrlParam("playerId");
//            UUID playerUUID = playerIdWithCUUIDMap.getOrDefault(playerId, null);
//            /* 帐号重复登入 后踢前 */
//            if (playerUUID != null) {
//                SocketIOClient oldClient = socketIoServer.getClient(playerUUID);
//                SendMsgDto sendMsgDto = SocketUtil.buildSendMsgDto(null, CmdEnum.REPEAT_PLAYER_LOGIN);
//                SocketUtil.sendEvent(oldClient, Constants.MESSAGE_EVENT_TO_PLAYER, sendMsgDto);
//                // 前端控制断线
////                oldClient.disconnect();
//            }
            /* 連接 存入 client */
//            playerIdWithCUUIDMap.put(playerId, client.getSessionId());
        } catch (Exception e) {
            log.error("初始连线发生错误");
        }
    }

    /**
     * 添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息
     **/
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        // 移除 该客户连线纪录
        playerIdWithCUUIDMap.values().remove(client.getSessionId());
        String sessionId = client.getSessionId().toString();
        log.info("SocketEvent 客户端断开连接, sessionId = {}", sessionId);
        client.disconnect();
    }


    /**
     * 獲取client的ip
     *
     * @param client
     * @return
     */
    public String getIPString(SocketIOClient client) {
        String ipString = "";
        String socketString = client.getRemoteAddress().toString();
        int colonAt = socketString.indexOf(":");
        ipString = socketString.substring(1, colonAt);
        return ipString;
    }


    public static Map<String, UUID> getPlayerIdWithCUUIDMap() {
        return playerIdWithCUUIDMap;
    }
}
