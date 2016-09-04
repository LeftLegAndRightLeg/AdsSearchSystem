package com.main.config;

/**
 * Created by gongbailiang on 9/1/16.
 */
import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:/aws-config.xml")
@EnableRdsInstance(databaseName = "AdsSearchSystem",
        dbInstanceIdentifier = "adssearchsystem-db",
        password = "")
public class AwsResourceConfig {

}
