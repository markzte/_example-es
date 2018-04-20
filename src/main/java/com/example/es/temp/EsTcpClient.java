package com.example.es.temp;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by shilei on 16/9/23.
 */
public class EsTcpClient {

    private static final String ES_HOST = "localhost";
    private static final int ES_TCP_PORT = 9300;

    private static TransportClient client;

    /**
     * 获取TCP 客户端
     *
     * @return
     */
    public static synchronized TransportClient getClient() {
        if (client == null) {
            build();
        }
        return client;
    }

    /**
     * 关闭客户端
     */
    public static void close(TransportClient client) {
        if (client != null) {
            client.close();
        }
    }

    /**
     * 建立连接
     *
     * @return
     */
    private static void build() {
        try {
            //特别注意：如果cluster 起了名字，需要在连接时指定名字，否则验证客户端连接的不是默认集群elasticsearch，会忽略，则无法找到节点
        	TransportClient client = TransportClient.builder().build()
        	        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host1"), 9300))
        	        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host2"), 9300));

            // on shutdown


//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host2"), 9300));
        } catch (UnknownHostException e) {
        	client.close();
            throw new RuntimeException(e);
        }
    }

}
