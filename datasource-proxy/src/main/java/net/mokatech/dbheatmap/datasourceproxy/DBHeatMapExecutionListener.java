package net.mokatech.dbheatmap.datasourceproxy;

import net.mokatech.dbheatmap.HeatMap;
import net.mokatech.dbheatmap.ContextHolder;
import net.mokatech.dbheatmap.Table;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;

import java.util.List;

public class DBHeatMapExecutionListener implements QueryExecutionListener {

    private final HeatMap heatMap;
    private final ContextHolder contextHolder;

    public DBHeatMapExecutionListener(HeatMap heatMap, ContextHolder contextHolder) {
        this.heatMap = heatMap;
        this.contextHolder = contextHolder;
    }

    public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
    }

    public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        try {
            if (queryInfoList != null) {
                for (QueryInfo queryInfo : queryInfoList) {
                    Statement stmt = CCJSqlParserUtil.parse(queryInfo.getQuery());
                    TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
                    List<String> tableList = tablesNamesFinder.getTableList(stmt);
                    tableList.forEach(t -> heatMap.addHeat(Table.of(t), contextHolder.getCurrentContext()));
                }
            }
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }
}
