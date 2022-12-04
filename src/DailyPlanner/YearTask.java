package DailyPlanner;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class YearTask extends DailyPlanner {

    public YearTask(String headline, String description, LocalDateTime dateOfCompletion, TaskType taskType) {
        super(headline, description, dateOfCompletion, taskType);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        LocalDate taskDate = this.getTaskDateTime().toLocalDate();
        return taskDate.equals(localDate) || (localDate.isAfter(taskDate)
                && localDate.getDayOfYear() == taskDate.getDayOfYear());
    }

    @Override
    public Repeatable getRepeatableType() {
        return Repeatable.YEARLY;
    }
}
