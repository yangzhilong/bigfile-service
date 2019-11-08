/**
 * @author edwin
 * @email edwin.zhao@nike.com
 * @date 2019-07-04
*/
package com.longge.bigfile.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	public S3Client getAmazonS3(S3Config config) {
	    AwsBasicCredentials awsCredential = AwsBasicCredentials.create(
	        config.getAccessKeyId(),
	        config.getSecretAccessKey());
		S3Client s3Client = S3Client.builder().region(Region.of(config.region)).credentialsProvider(StaticCredentialsProvider.create(awsCredential)).build();
		return s3Client;
	}
	
	@Configuration
	@ConfigurationProperties(prefix = "s3")
	@Getter
	@Setter
	public static class S3Config {
	    private String bucketName;
	    
	    private String accessKeyId;
	    
	    private String secretAccessKey;
	    
	    private String region;
	}
}
