package cn.colins.canal.type;


public class LongTypeHandler extends BaseTypeHandler<Long> {
    @Override
    public Long convert(Object value) {
        return new Long(value.toString());
    }
}
