package cn.colins.canal.reflector;



import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 封装filed元数据信息
 */
public class SetFieldInvoker implements Invoker {
    private Field field;

    public SetFieldInvoker(Field field) {
        this.field = field;
    }

    //给指定对象的当前属性设置值
    @Override
    public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
        this.field.set(target, args[0]);
        return null;
    }

    @Override
    public Class<?> getType() {
        return this.field.getType();
    }
}
