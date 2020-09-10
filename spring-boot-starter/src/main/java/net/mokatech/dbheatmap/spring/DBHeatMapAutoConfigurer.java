package net.mokatech.dbheatmap.spring;

import net.mokatech.dbheatmap.HeatMap;
import net.mokatech.dbheatmap.ContextHolder;
import net.mokatech.dbheatmap.spring.annotation.DBHeatMapContextAspect;
import net.mokatech.dbheatmap.spring.controller.DBHeatMapController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBHeatMapAutoConfigurer {

    @Bean
    @ConditionalOnMissingBean
    public HeatMap dbHeatMap() {
        return new HeatMap();
    }

    @Bean
    @ConditionalOnMissingBean
    public ContextHolder dbHeatMapContextHolder() {
        return new ContextHolder();
    }

    @Bean
    @ConditionalOnMissingBean
    public DBHeatMapContextAspect dbHeatMapContextAspect() {
        return new DBHeatMapContextAspect(dbHeatMapContextHolder());
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication
    public DBHeatMapController controller(HeatMap heatMap) {
        return new DBHeatMapController(heatMap);
    }

}
