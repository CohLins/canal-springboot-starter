package cn.colins.canal.reflector;

import java.lang.reflect.InvocationTargetException;


public interface Invoker {
    Object invoke(Object var1, Object[] var2) throws IllegalAccessException, InvocationTargetException;

    Class<?> getType();
}
