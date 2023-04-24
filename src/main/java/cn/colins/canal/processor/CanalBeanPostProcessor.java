package cn.colins.canal.processor;

import cn.colins.canal.annotation.CanalInstanceAnnotation;
import cn.colins.canal.annotation.CanalServerAnnotation;
import cn.colins.canal.config.CanalServerConfig;
import cn.colins.canal.core.CanalFactory;
import cn.colins.canal.core.CanalDataHandler;
import cn.colins.canal.entity.CanalInstanceInfo;
import cn.colins.canal.entity.CanalServerInfo;
import cn.colins.canal.exception.CanalException;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashSet;


public class CanalBeanPostProcessor implements BeanPostProcessor {

    private CanalServerConfig canalServerConfig;

    public CanalBeanPostProcessor(CanalServerConfig canalServerConfig) {
        this.canalServerConfig = canalServerConfig;
        Assert.notNull(canalServerConfig.getServerInfos(), "canal server config list is null");
        canalServerConfig.getServerInfos().forEach(canalServerInfo -> {
            Assert.notNull(canalServerInfo.getAddress(), "canal server address list is null");
            Assert.notNull(canalServerInfo.getAlias(), "canal server alias list is null");
            canalServerInfo.setInstanceInfos(new HashSet<>());
            Assert.isTrue(CanalFactory.CANAL_SERVER_CONFIG.put(canalServerInfo.getAlias(), canalServerInfo) == null, "canal server alias or address non-repeatable");
        });
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        if (CanalDataHandler.class.isAssignableFrom(aClass)) {
            CanalServerAnnotation annotation = aClass.getAnnotation(CanalServerAnnotation.class);
            if (annotation != null) {
                getCanalServerAnnotationInfo(annotation, aClass,bean);
            }
        }
        return bean;
    }

    private void getCanalServerAnnotationInfo(CanalServerAnnotation annotation, Class<?> beanClass,Object bean) {
        CanalServerInfo canalServerInfo = CanalFactory.CANAL_SERVER_CONFIG.get(annotation.alias());
        if (canalServerInfo == null) {
            throw new CanalException(beanClass.getName() + " No matching service alias ");
        }
        for (int i = 0, length = annotation.instances().length; i < length; i++) {
            CanalInstanceAnnotation instance = annotation.instances()[i];
            boolean add = canalServerInfo.getInstanceInfos().add(new CanalInstanceInfo(annotation.alias(),instance.destination(), instance.subscribe(),
                    instance.batchSize(), instance.getMsgTimeout()));
            Assert.isTrue(add,"server: " + annotation.alias() + " have two identical instances ");
            CanalFactory.registerDataHandler(annotation.alias(),instance.destination(),(CanalDataHandler) bean);
            CanalFactory.registerDataHandler(annotation.alias(),instance.destination(),(CanalDataHandler) bean);

        }
    }
}
