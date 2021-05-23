package com.htech.socketio_demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 本项目自定义配置
 *
 * @author xiongneng
 * @since 2018/01/06 21:09
 */
@Component
@ConfigurationProperties(prefix = "socketio")
@Getter
@Setter
public class SocketProperties {
    /**
     * socket端口
     */
    private Integer socketPort;
    /**
     * Ping消息间隔（毫秒）
     */
    private Integer pingInterval;
    /**
     * Ping消息超时时间（毫秒）
     */
    private Integer pingTimeout;

    private Integer upgradeTimeout;

    private Boolean allowCustomRequests;

    private Integer maxHttpContentLength;

    private Integer maxFramePayloadLength;

    private String[] nameSpaces;
    /**
     * APK文件访问URL前缀
     */
    private String apkUrlPrefix;

    private Integer bossThread;
    private Integer workThread;
    private Integer acceptBackLog;
    private Integer sendBufferSize;

    private boolean useLinuxNativeEpoll;

    private boolean reuseAddress;
    private boolean tcpKeepAlive;

    private int writeBufferHighWaterMark;
    private int writeBufferLowWaterMark;

}
