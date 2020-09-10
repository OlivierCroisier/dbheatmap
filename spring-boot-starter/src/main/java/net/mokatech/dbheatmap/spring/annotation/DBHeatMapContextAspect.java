package net.mokatech.dbheatmap.spring.annotation;

import net.mokatech.dbheatmap.ContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DBHeatMapContextAspect {

    private final ContextHolder contextHolder;

    public DBHeatMapContextAspect(ContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }

    @Before("@annotation(dbHeatMapContextAnnotation)")
    public void foo(DBHeatMapContext dbHeatMapContextAnnotation) {
        this.contextHolder.setCurrentContext(dbHeatMapContextAnnotation.value());
    }

}
