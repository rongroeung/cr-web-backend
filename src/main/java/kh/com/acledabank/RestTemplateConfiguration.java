package kh.com.acledabank;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.GeneralSecurityException;

@Configuration
public class RestTemplateConfiguration {
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate(requestFactory());
    }

    private CloseableHttpClient httpClient(){
        return HttpClients.custom()
                .setConnectionManager(poolingConnectionManager())
                .build();
    }

    private HttpComponentsClientHttpRequestFactory requestFactory() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(30000);
        requestFactory.setConnectionRequestTimeout(30000);
        return requestFactory;
    }

    private PoolingHttpClientConnectionManager poolingConnectionManager() {

        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext(), NoopHostnameVerifier.INSTANCE);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory)
                .build();


        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        poolingConnectionManager.setMaxTotal(1000);
        poolingConnectionManager.setDefaultMaxPerRoute(100);
        return poolingConnectionManager;
    }

    private SSLContext sslContext(){
        try {
            return new SSLContextBuilder().loadTrustMaterial(null, new TrustAllStrategy()).build();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

}