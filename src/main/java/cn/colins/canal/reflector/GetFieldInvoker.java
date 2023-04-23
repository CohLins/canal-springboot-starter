package cn.colins.canal.reflector;



import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 封装Filed信息 提供getfiled的调用实现
 */
public class GetFieldInvoker implements Invoker {
    private Field field;

    public GetFieldInvoker(Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
        return this.field.get(target);
    }

    @Override
    public Class<?> getType() {
        return this.field.getType();
    }
}