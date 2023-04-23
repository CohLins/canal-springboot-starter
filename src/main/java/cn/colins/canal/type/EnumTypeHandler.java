package cn.colins.canal.type;

import java.text.ParseException;


public class EnumTypeHandler<E extends Enum<E>>  extends BaseTypeHandler<E>{
    private final Class<E> type;
    public EnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        } else {
            this.type = type;
        }
    }
    @Override
    public E convert(Object value) throws ParseException {
        return Enum.valueOf(type,value.toString());
    }
}
