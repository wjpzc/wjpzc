package cn.xypt.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


    @Configuration
    public class MinioConfig {
        @Value("${my.minio.endpoint}")
        private String endpoint;
        @Value("${my.minio.port}")
        private Integer port;
        @Value("${my.minio.accessKey}")
        private String accessKey;
        @Value("${my.minio.secretKey}")
        private String secretKey;
        @Value("${my.minio.secure}")
        private Boolean secure;
        @Bean
        public MinioClient minioClient(){
            return MinioClient.builder()
                    .endpoint(endpoint,port,secure)
                    .credentials(accessKey, secretKey)
                    .build();
        }
    }

