package DailyPlanner;

import java.time.LocalDate;
import java.util.*;

public class Schedule {
    private final Map<Integer, DailyPlanner> planner = new HashMap<>();

    public void addTask(DailyPlanner dailyPlanner) {
        this.planner.put(dailyPlanner.getId(), dailyPlanner);
    }

    public void removeTask(int id) throws TasksNotFoundExeption {
        if (this.planner.containsKey(id)) {
            this.planner.remove(id);
        } else {
            throw new TasksNotFoundExeption();
        }
        this.planner.remove(id);
    }

    public Collection<DailyPlanner> getAllTasks() {
        return this.planner.values();
    }
    public Collection<DailyPlanner> getTasksForDay(LocalDate localDate) {
        TreeSet<DailyPlanner> tasksForDate = new TreeSet<>();
        for (DailyPlanner dailyPlanner : planner.values()) {
            if (dailyPlanner.appearsIn(localDate)) {
                tasksForDate.add(dailyPlanner);
            }
        }
        return tasksForDate;
    }
}
