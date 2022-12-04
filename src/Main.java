import DailyPlanner.DailyPlanner;
import DailyPlanner.Schedule;
import DailyPlanner.TaskType;
import DailyPlanner.WeekTask;
import DailyPlanner.MonthTask;
import DailyPlanner.YearTask;
import DailyPlanner.DailyTask;
import DailyPlanner.TasksNotFoundExeption;
import DailyPlanner.Repeatable;
import DailyPlanner.OneRepeatTask;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Scanner;

public class Main {
    private static final Schedule SCHEDULE = new Schedule();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SCHEDULE.addTask(new OneRepeatTask("Ужин","Приготовить ужин",LocalDateTime.now().plusMinutes(15),TaskType.PERSONAL));
        SCHEDULE.addTask(new DailyTask("Уборка","Убраться в квартире",LocalDateTime.now().plusMinutes(10),TaskType.PERSONAL));
        SCHEDULE.addTask(new WeekTask("Отчет","Сделать отчет по работе",LocalDateTime.now().plusMinutes(7),TaskType.WORK));
        SCHEDULE.addTask(new MonthTask("Отчет","Сделать отчет по работе",LocalDateTime.now().plusMinutes(9),TaskType.WORK));
        SCHEDULE.addTask(new YearTask("Отчет","Сделать отчет по работе",LocalDateTime.now().plusMinutes(8),TaskType.WORK));
        addTask(scanner);
        printTaskForDate(scanner);
    }

    private static void addTask(Scanner scanner) {
        String headline = readString("Введите название задачи: ",scanner);
        String description = readString("Введите описание задачи: ", scanner);
        LocalDateTime taskDate = readDateTime(scanner);
        TaskType taskType = readTaskType(scanner);
        Repeatable repeatable = repeatableTask(scanner);
        DailyPlanner dailyPlanner = null;
        switch (repeatable) {
            case SINGLE:
                dailyPlanner = new OneRepeatTask(headline, description, taskDate, taskType);
            case DAILY:
                dailyPlanner = new DailyTask(headline, description, taskDate, taskType);
            case WEEKLY:
                dailyPlanner = new WeekTask(headline, description, taskDate, taskType);
            case MONTHLY:
                dailyPlanner = new MonthTask(headline, description, taskDate, taskType);
            case YEARLY:
                dailyPlanner = new YearTask(headline, description, taskDate, taskType);
        }
        SCHEDULE.addTask(dailyPlanner);
    }
    private static Repeatable repeatableTask(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Виберите тип повторяемости: ");
                for (Repeatable repeatable : Repeatable.values()) {
                    System.out.println(repeatable.ordinal() + ". " + localizeRepeatable(repeatable));
                }
                System.out.println("Введите тип : ");
                String ordinalLine = scanner.nextLine();
                int ordinal = Integer.parseInt(ordinalLine);
                return Repeatable.values()[ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Введен неверный номер типа задачи");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип задачи не найден");
            }

        }
    }

    private static TaskType readTaskType(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Виберите тип задачи: ");
                for (TaskType taskType : TaskType.values()) {
                    System.out.println(taskType.ordinal() + ". " + localizeType(taskType));
                }
                System.out.println("Введите тип задачи: ");
                String ordinalLine = scanner.nextLine();
                int ordinal = Integer.parseInt(ordinalLine);
                return TaskType.values()[ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Введен неверный номер типа задачи");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип задачи не найден");
            }

        }
    }

    private static LocalDateTime readDateTime(Scanner scanner) {
        LocalDate localDate = readDate(scanner);
        LocalTime localTime = readTime(scanner);
        return localDate.atTime(localTime);
    }

    private static String readString(String message, Scanner scanner) {
        while (true) {
            System.out.print(message);
            String readString = scanner.nextLine();
            if (readString == null || readString.isEmpty() || readString.isBlank()) {
                System.out.println(" Введен неверный формат даты и времени");
            } else {
                return readString;
            }

        }

    }

    private static void removeTask(Scanner scanner) {
        System.out.println("Все задачи");
        for (DailyPlanner dailyPlanner: SCHEDULE.getAllTasks()) {
            System.out.printf("%d. %s [%s](%s)%n",dailyPlanner.getId(),dailyPlanner.getHeadline()
                    ,localizeType(dailyPlanner.getTaskType()),localizeRepeatable(dailyPlanner.getRepeatableType()));
        }
        while (true) {
            try {
                System.out.println("Виберите задачу которую хотите удалить: ");
                String idLine = scanner.nextLine();
                int id = Integer.parseInt(idLine);
                SCHEDULE.removeTask(id);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введен неверный id задачи");
            } catch (TasksNotFoundExeption e) {
                System.out.println("Задача не найдена");
            }

        }

    }

    private static void printTaskForDate(Scanner scanner) {
        LocalDate localDate = readDate(scanner);
        Collection<DailyPlanner> tasksForDay = SCHEDULE.getTasksForDay(localDate);
        System.out.println("Задачи на " + localDate.format(DATE_FORMATTER));
        for (DailyPlanner dailyPlanner : tasksForDay) {
            System.out.printf("[%s]%s: %s (%s)%n",localizeType(dailyPlanner.getTaskType()),dailyPlanner.getHeadline()
                    ,dailyPlanner.getTaskDateTime().format(TIME_FORMATTER)
                    ,dailyPlanner.getDescription());
        }
    }

    private static LocalDate readDate(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Введите дату задачи в формате dd.MM.yyyy: ");
                String dateLine = scanner.nextLine();
                return LocalDate.parse(dateLine, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты и времени");
            }
        }

    }
    private static LocalTime readTime(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Введите время задачи в формате HH:mm: ");
                String timeLine = scanner.nextLine();
                return LocalTime.parse(timeLine, TIME_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат времени");
            }
        }

    }

    private static String localizeType(TaskType taskType) {
        String type;
        switch (taskType) {
            case WORK:
                type = "Рабочая задача";
                break;
            case PERSONAL:
                type = "Личная задача";
                break;
            default:
                type = "Неизвестная задача";
                break;
        }
        return type;
    }

    private static String localizeRepeatable(Repeatable repeatable) {
        String repeat;
        switch (repeatable) {
            case SINGLE:
                repeat = "Разовая задача";
                break;
            case DAILY:
                repeat = "Ежедневная задача";
                break;
            case WEEKLY:
                repeat = "Еженедельная задача";
                break;
            case MONTHLY:
                repeat = "Ежемесячная задача";
            case YEARLY:
                repeat = "Ежегодная задача";
                break;
            default:
                repeat = "Неизвестный формат повторения";
                break;
        }
        return repeat;
    }
}