package DailyPlanner;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class DailyPlanner implements Comparable<DailyPlanner> {
    private static int idCounter = 0;
    private final int id;
    private final String headline;
    private final String description;
    private final LocalDateTime taskDateTime;
    private final TaskType taskType;

    public DailyPlanner(String headline, String description, LocalDateTime dateOfCompletion, TaskType taskType) {
        this.headline = headline;
        this.description = description;
        this.taskDateTime = dateOfCompletion;
        this.taskType = taskType;
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }

    public String getHeadline() {
        return headline;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTaskDateTime() {
        return taskDateTime;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public abstract boolean appearsIn(LocalDate localDate);

    public abstract Repeatable getRepeatableType();

    @Override
    public int compareTo(DailyPlanner o) {
        if (o == null) {
            return 1;
        }
        return this.taskDateTime.toLocalTime().compareTo(o.taskDateTime.toLocalTime());
    }
}
