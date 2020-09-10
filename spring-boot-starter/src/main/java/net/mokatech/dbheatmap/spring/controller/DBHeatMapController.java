package net.mokatech.dbheatmap.spring.controller;


import net.mokatech.dbheatmap.HeatMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Predicate;

@RestController
@RequestMapping("${dbheatmap.controller.mapping:/dbheatmap}")
public class DBHeatMapController {

    private final HeatMap heatMap;

    public DBHeatMapController(HeatMap heatMap) {
        this.heatMap = heatMap;
    }

    @GetMapping()
    public ResponseEntity<?> getHeatMap(
            @RequestParam(value = "context", required = false) String context,
            @RequestParam(value = "aggregate", required = false) boolean aggregate
    ) {
        Predicate<String> filter = __ -> true;
        if (context != null && context.trim().length() > 0) {
            filter = context::equals;
        }
        if (aggregate) {
            return ResponseEntity.ok(heatMap.getAggregatedHeatMap(filter));
        } else {
            return ResponseEntity.ok(heatMap.getHeatMap(filter));
        }
    }

    @GetMapping("/clear")
    @DeleteMapping()
    public ResponseEntity<?> clearHeatMap() {
        heatMap.clear();
        return ResponseEntity.ok("{status: \"OK\"}");
    }

}
