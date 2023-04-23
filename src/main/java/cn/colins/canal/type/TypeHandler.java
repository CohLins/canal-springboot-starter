package cn.colins.canal.type;

import java.text.ParseException;


public interface TypeHandler<T> {
    public T convert(Object value) throws ParseException;
}
