 package com.longge.bigfile.util;

import java.util.Map;

import com.longge.bigfile.config.AmazonS3Configuration.S3Config;

import software.amazon.awssdk.services.s3.S3Client;

/**
 * @author roger yang
 * @date 12/11/2019
 */
public class S3ClientUtils {
    private static Map<String, S3Client> s3Clients;
    private static Map<String, S3Config> s3Configs;
    
    public static void setS3Clients(Map<String, S3Client> clients) {
        s3Clients = clients;
    }
    
    public static void setS3Configs(Map<String, S3Config> configs) {
        s3Configs = configs;
    }
    
    public static S3Client getClient(String sys) {
        S3Client client = s3Clients.get(sys);
        if(null == client) {
            throw new RuntimeException("can't find project " + sys + " s3 info");
        }
        return client;
    }
    
    public static S3Config getConfig(String sys) {
        S3Config config = s3Configs.get(sys);
        if(null == config) {
            throw new RuntimeException("can't find project " + sys + " s3 info");
        }
        return config;
    }
}
