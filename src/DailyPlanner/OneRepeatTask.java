package DailyPlanner;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OneRepeatTask extends DailyPlanner {
    public OneRepeatTask(String headline, String description, LocalDateTime dateOfCompletion, TaskType taskType) {
        super(headline, description, dateOfCompletion, taskType);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        return localDate.equals(this.getTaskDateTime().toLocalDate());
    }

    @Override
    public Repeatable getRepeatableType() {
        return Repeatable.SINGLE;
    }
}
