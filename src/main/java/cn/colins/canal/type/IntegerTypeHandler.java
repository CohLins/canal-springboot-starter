package cn.colins.canal.type;


public class IntegerTypeHandler extends BaseTypeHandler<Integer> {
    @Override
    public Integer convert(Object value) {
        return new Integer(value.toString());
    }
}
