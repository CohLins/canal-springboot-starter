package cn.colins.canal.type;

import java.text.ParseException;


public class StringTypeHandler extends BaseTypeHandler<String> {
    @Override
    public String convert(Object value) throws ParseException {
        return value.toString();
    }
}
