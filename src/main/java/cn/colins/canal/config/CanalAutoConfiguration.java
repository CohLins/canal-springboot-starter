package cn.colins.canal.config;



import cn.colins.canal.core.CanalFactory;
import cn.colins.canal.processor.CanalBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties({CanalServerConfig.class})
public class CanalAutoConfiguration {

    @Bean
    @ConditionalOnProperty(value = "canal.server.enable" ,havingValue = "true" )
    public CanalBeanPostProcessor canalBeanPostProcessor(CanalServerConfig canalServerConfig){
        return new CanalBeanPostProcessor(canalServerConfig);
    }

    @Bean
    @ConditionalOnBean(CanalBeanPostProcessor.class)
    public CanalFactory canalFactory(CanalServerConfig canalServerConfig){
        return new CanalFactory();
    }
}
