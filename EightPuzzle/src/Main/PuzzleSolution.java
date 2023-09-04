package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PuzzleSolution {
    private List<Direction> path;
    private long executionTime; // in milliseconds
    private long memoryUsage; // in kilobytes
    private HashMap<String, Object> metrics;

    public PuzzleSolution() {
        this.path = new ArrayList<>();
        this.metrics = new HashMap<>();
    }


	public void setPath(List<Direction> path) {
		this.path = path;
	}
    public List<Direction> getPath() {
        return path;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
    public long getExecutionTime() {
        return executionTime;
    }

    public void setMemoryUsage(long memoryUsage) {
        this.memoryUsage = memoryUsage;
    }
    public long getMemoryUsage() {
        return memoryUsage;
    }
    
    public void putMetric(String key, Object value) {
        this.metrics.put(key, value);
    }

    public Object getMetric(String key) {
        return this.metrics.get(key);
    }
}

