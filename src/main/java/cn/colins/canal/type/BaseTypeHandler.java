package cn.colins.canal.type;

import java.text.ParseException;


public abstract class BaseTypeHandler<T> implements TypeHandler<T> {
    public T convertNullableResult(Object value) throws ParseException {
        if(value==null){
            return null;
        }
        return convert(value);
    }
    public abstract T convert(Object value) throws ParseException;

}
