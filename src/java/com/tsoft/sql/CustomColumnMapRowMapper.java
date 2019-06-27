/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.sql;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.number.NumberFormatter;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.support.JdbcUtils;

public class CustomColumnMapRowMapper extends ColumnMapRowMapper {
    public CustomColumnMapRowMapper() {
    }

    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Map<String, Object> mapOfColValues = this.createColumnMap(columnCount);

        for(int i = 1; i <= columnCount; ++i) {
            String key = this.getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
            Object obj = this.getColumnValue(rs, i);
            if (rsmd.getColumnType(i) == 3) {
                NumberFormatter nf = new NumberFormatter();
                nf.setPattern("#,##0.00");
                mapOfColValues.put(key, obj != null ? nf.print((BigDecimal)obj, Locale.FRENCH) : null);
            } else {
                DateFormatter df = new DateFormatter();
                if (rsmd.getColumnType(i) == 93) {
                    df.setPattern("dd/MM/yyyy HH:mm:ss");
                    mapOfColValues.put(key, obj != null ? df.print((Date)obj, Locale.FRENCH) : null);
                } else if (rsmd.getColumnType(i) == 91) {
                    df.setPattern("dd/MM/yyyy");
                    mapOfColValues.put(key, obj != null ? df.print((Date)obj, Locale.FRENCH) : null);
                } else if (rsmd.getColumnType(i) == 92) {
                    df.setPattern("HH:mm");
                    mapOfColValues.put(key, obj != null ? df.print((Date)obj, Locale.FRENCH) : null);
                } else {
                    mapOfColValues.put(key, obj);
                }
            }
        }

        return mapOfColValues;
    }
}
