package pl.wsb.fitnesstracker.training.internal;

import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class TrainingReportScheduler {

    private final UserProvider userProvider;
    private final TrainingProvider trainingProvider;

    @Scheduled(cron = "0 0 0 * * MON")
    public void generateWeeklyReport() {
        log.info(">>> Rozpoczynam generowanie cotygodniowego raportu treningowego <<<");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date oneWeekAgo = calendar.getTime();

        List<User> users = userProvider.findAllUsers();

        for (User user : users) {
            List<Training> allTrainings = trainingProvider.getAllTrainingsForUser(user.getId());
            List<Training> weeklyTrainings = allTrainings.stream()
                    .filter(training -> {
                        return training.getEndTime().after(oneWeekAgo);
                    })
                    .collect(Collectors.toList());

            log.info("Raport dla użytkownika: {} {} (ID: {})", user.getFirstName(), user.getLastName(), user.getId());
            if (weeklyTrainings.isEmpty()) {
                log.info("\tBrak treningów w ostatnim tygodniu.");
            } else {
                log.info("\tLiczba treningów w tym tygodniu: {}", weeklyTrainings.size());
                for (Training training : weeklyTrainings) {
                    log.info("\t - Trening: {}, Data: {}, Dystans: {}",
                            training.getActivityType(),
                            training.getEndTime(),
                            training.getDistance());
                }
            }
        }

        log.info(">>> Koniec raportu <<<");
    }
}