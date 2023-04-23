package cn.colins.canal.type;

import java.math.BigInteger;


public class BigIntegerTypeHandler extends BaseTypeHandler<BigInteger> {
    @Override
    public BigInteger convert(Object value) {
        return new BigInteger(value.toString());
    }
}
