package cn.colins.canal.type;

import java.math.BigDecimal;


public class BigDecimalTypeHandler extends BaseTypeHandler<BigDecimal> {
    @Override
    public BigDecimal convert(Object value) {
        return  new BigDecimal(value.toString());
    }
}
