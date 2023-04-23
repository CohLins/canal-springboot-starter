package cn.colins.canal.type;


public class BooleanTypeHandler extends BaseTypeHandler<Boolean>{
    @Override
    public Boolean convert(Object value) {
        return new Boolean(value.toString());
    }
}
