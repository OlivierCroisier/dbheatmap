package net.mokatech.dbheatmap.spring.annotation;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DBHeatMapContext {
    String value();
}
