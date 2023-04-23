package cn.colins.canal.reflector;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 方法元数据封装 以及提供调用的方法
 */
public class MethodInvoker implements Invoker {
    private Class<?> type;
    private Method method;

    public MethodInvoker(Method method) {
        this.method = method;
        if (method.getParameterTypes().length == 1) {
            this.type = method.getParameterTypes()[0];
        } else {
            this.type = method.getReturnType();
        }

    }

    @Override
    public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
        return this.method.invoke(target, args);
    }

    @Override
    public Class<?> getType() {
        return this.type;
    }
}