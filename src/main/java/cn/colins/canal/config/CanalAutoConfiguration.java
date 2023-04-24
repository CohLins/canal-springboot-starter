package cn.colins.canal.config;



import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties({CanalServerConfig.class})
public class CanalAutoConfiguration {

}
