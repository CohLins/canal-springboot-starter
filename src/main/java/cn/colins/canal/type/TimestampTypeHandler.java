package cn.colins.canal.type;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class TimestampTypeHandler   extends BaseTypeHandler<Timestamp> {
    @Override
    public Timestamp convert(Object value) throws ParseException {
        String  valueStr=value.toString();
        if(valueStr.indexOf("-")>0){
            SimpleDateFormat sdf  =   new  SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            return new Timestamp(sdf.parse(valueStr).getTime());
        }
        return new Timestamp(Long.valueOf(value.toString()));
    }
}
