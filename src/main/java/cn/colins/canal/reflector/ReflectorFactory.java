package cn.colins.canal.reflector;


import cn.colins.canal.exception.ReflectionException;

public interface ReflectorFactory {

    boolean isClassCacheEnabled();

    void setClassCacheEnabled(boolean var1);

    Reflector findForClass(Class<?> var1) throws ReflectionException;
}