package cn.colins.canal.reflector;


import cn.colins.canal.exception.ReflectionException;

import java.util.List;


public interface ObjectFactory {

    <T> T create(Class<T> var1) throws ReflectionException;

    <T> T create(Class<T> var1, List<Class<?>> var2, List<Object> var3) throws ReflectionException;

    <T> boolean isCollection(Class<T> var1);
}