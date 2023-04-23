package cn.colins.canal.type;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateTypeHandler extends BaseTypeHandler<Date> {

    @Override
    public Date convert(Object value) throws ParseException {
        String  valueStr=value.toString();
        if(valueStr.indexOf("-")>0){
            SimpleDateFormat sdf  =   new  SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            return sdf.parse(valueStr);
        }
        return new Timestamp(Long.valueOf(value.toString()));
    }
}
