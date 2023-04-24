package cn.colins.canal.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CanalInstanceAnnotation {


    /**
     * 实例名称
     **/
    String destination();

    /**
     * 监听表的表达式
     **/
    String subscribe() default "*.*";

    /**
     * 一次拉取多少条消息
     **/
    int batchSize() default 1000;

    /**
     * 拉取消息等待时长 单位：毫秒
     **/
    long getMsgTimeout() default 1000;
}
