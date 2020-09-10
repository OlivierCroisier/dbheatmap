package net.mokatech.dbheatmap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HeatMap {

    private final ConcurrentHashMap<Table, Map<String, LongAdder>> heatMap = new ConcurrentHashMap<>();

    public void addHeat(Table table, String context) {
        Map<String, LongAdder> tableHeatMap = this.heatMap.computeIfAbsent(table, t -> new ConcurrentHashMap<>());
        LongAdder counter = tableHeatMap.computeIfAbsent(context, t -> new LongAdder());
        counter.increment();
    }

    public void clear() {
        this.heatMap.clear();
    }

    public Map<Table, Map<String, LongAdder>> getHeatMap() {
        return this.getHeatMap(__ -> true);
    }

    public Map<Table, Map<String, LongAdder>> getHeatMap(Predicate<String> contextFilter) {
        ConcurrentHashMap<Table, Map<String, LongAdder>> map = new ConcurrentHashMap<>(this.heatMap);
        map.replaceAll((table, counters) -> counters.entrySet().stream()
                .filter(e -> contextFilter.test(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        return map;
    }

    public Map<Table, Long> getAggregatedHeatMap() {
        return this.getAggregatedHeatMap(__ -> true);
    }

    public Map<Table, Long> getAggregatedHeatMap(Predicate<String> contextFilter) {
        Map<Table, Long> map = new HashMap<>();
        this.heatMap.forEach((key, val) -> {
            Long total = val.entrySet().stream()
                    .filter(e -> contextFilter.test(e.getKey()))
                    .map(Map.Entry::getValue)
                    .reduce(0L, (acc, LongAdder) -> acc + LongAdder.longValue(), Long::sum);
            map.put(key, total);
        });
        return map;
    }

}
