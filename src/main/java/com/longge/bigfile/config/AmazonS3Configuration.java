/**
 * @author edwin
 * @email edwin.zhao@nike.com
 * @date 2019-07-04
*/
package com.longge.bigfile.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.longge.bigfile.util.S3ClientUtils;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * 
 * @author roger yang
 * @date 11/04/2019
 */
@Configuration
public class AmazonS3Configuration {
	
	@Bean
	public S3ClientBootstrap getAmazonS3(S3MulitiConfig configs) {
		Map<String, S3Client> s3Clients = new HashMap<>();
	    Map<String, S3Config> s3Configs = new HashMap<>();
	    
	    configs.getProjects().entrySet().forEach(entry -> {
	        S3Config config = entry.getValue();
	        AwsBasicCredentials awsCredential = AwsBasicCredentials.create(
	            config.getAccessKeyId(),
	            config.getSecretAccessKey());
	        S3Client s3Client = S3Client.builder().region(Region.of(config.region)).credentialsProvider(StaticCredentialsProvider.create(awsCredential)).build();
	        
	        s3Clients.put(entry.getKey(), s3Client);
	        s3Configs.put(entry.getKey(), config);
	    });
	    S3ClientUtils.setS3Clients(s3Clients);
	    S3ClientUtils.setS3Configs(s3Configs);
		return null;
	}
	
	@Configuration
	@ConfigurationProperties(prefix = "s3")
	@Getter
	@Setter
	public static class S3MulitiConfig {
	    private Map<String, S3Config> projects;
	}
	
	@Getter
	@Setter
	public static class S3Config {
	    private String bucketName;
	    
	    private String accessKeyId;
	    
	    private String secretAccessKey;
	    
	    private String region = "cn-north-1";
	}
	
	static class S3ClientBootstrap {}
}
