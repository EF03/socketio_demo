package com.htech.socketio_demo;


import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * SocketClient
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2018/1/18
 */
public class SocketClient {
    private static Socket socket;
    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);
    private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJkc2IiLCJleHAiOjE2MDc3NTQ2NDksImlhdCI6MTYwNzY2ODI0OSwianRpIjoiMTYwNzY2ODI0OTY5NSJ9.SJTEtGvzhjvaMmUqVVTuzhCKFYIG4R5n1OnZq9a3wHwf4bIagaluhvt27W3uEfnJ8N-QJ_m-Mj9-a8iA3hLytQ";
    private static final String MESSAGE = "125193130d18d5d0e89fd7d5670ddeade4beceac37925048fe51b9ba24bb88ff34e8b7b8af273e5e3193db8b9cb27e83715ad8044dd37bacb5443aeac9ed1ff5f9d620bb0db4b762b811e3b6f100ff73cec88c6c85993cf0369d53fc080d7144c615311e404131c5c54e448229aebb27f4ec5322c5095fca6736a306672016457e82c4bd5a620ff83a579d921198dbe006c223fecfc3340bd0249395ba49cfe3ea19b435e5089bde469abe09a5e583e943d9a136ca203a071d9f56dce0bbf9c450494083209ddd3b7287221616b69adad9053183feeff79c9571ab26217b5fc14c4e198b129706f2fa5ce14830a21aff0738449b997bb578cf641bd3594981fb0e4b075c5839370a2f674e49cb4cda43";

    public static void main(String[] args) throws URISyntaxException, InterruptedException {

        IO.Options options = IO.Options.builder()
                .setForceNew(true)
                .setMultiplex(false)
//                .setPath("/test")
                .setTransports(new String[]{WebSocket.NAME})
                .build();

        String url = "http://localhost:9100";
//        Socket socket = IO.socket(URI.create(url), options); // the main namespace
        Socket testSocket = IO.socket(URI.create(url + "/test"), options); // the "test" namespace

        for (int i = 0; i < 10; i++) {
            testSocket.connect();

//            socket.emit("toServer", JSONUtil.parseObj(sendCmdDto), (Ack) args1 -> {
//                logger.info("回执消息=" + Arrays.stream(args1).map(Object::toString).collect(Collectors.joining(",")));
//            });

            Thread.sleep(100); // 暫停3秒

        }
    }
}
