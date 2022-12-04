package DailyPlanner;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class WeekTask extends DailyPlanner {

    public WeekTask(String headline, String description, LocalDateTime dateOfCompletion, TaskType taskType) {
        super(headline, description, dateOfCompletion, taskType);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        LocalDate taskDate = this.getTaskDateTime().toLocalDate();
        return localDate.equals(taskDate) || (localDate.isAfter(taskDate)
                && localDate.getDayOfWeek().equals(taskDate.getDayOfWeek()));
    }

    @Override
    public Repeatable getRepeatableType() {
        return Repeatable.WEEKLY;
    }
}
