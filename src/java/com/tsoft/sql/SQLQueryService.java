/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.sql;

import com.tsoft.utils.web.datatables.DatatableColumnModel;
import com.tsoft.web.model.ListModel;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

@Service
public class SQLQueryService {
    @Autowired
    JdbcTemplate jt;

    public SQLQueryService() {
    }

    public List<Map<String, Object>> retrieveQuery(String query) throws Exception {
        return this.jt.query(query, new CustomColumnMapRowMapper());
    }

    public ListModel getlistmodel(String query) throws Exception {
        ListModel result = new ListModel();
        this.jt.query(query, (rs) -> {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            for(int i = 1; i <= columnCount; ++i) {
                DatatableColumnModel cm = new DatatableColumnModel();
                cm.setaTargets(i - 1);
                cm.setsTitle(rsmd.getColumnName(i));
                cm.setName(rsmd.getColumnName(i));
                cm.setData(rsmd.getColumnName(i));
                result.addColumns(cm);
            }

            return columnCount;
        });
        return result;
    }
}
