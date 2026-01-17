package plwsb.fitnesstracker.training.internal;

import ch.qos.logback.classic.Logger;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
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
        log.info(">>> Rozpoczynam generowanie cotygodniowego raportu treningowego (ZADANIE 1) <<<");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date oneWeekAgo = calendar.getTime();

        List<User> users = userProvider.findAllUsers();

        for (User user : users) {
            List<Training> allTrainings = trainingProvider.getAllTrainingsForUser(user.getId());

            List<Training> weeklyTrainings = allTrainings.stream()
                    .filter(training -> training.getEndTime().after(oneWeekAgo))
                    .collect(Collectors.toList());

            log.info("--------------------------------------------------");
            log.info("Raport treningowy dla użytkownika: {} {} (ID: {})",
                    user.getFirstName(), user.getLastName(), user.getId());

            if (weeklyTrainings.isEmpty()) {
                log.info(" -> Brak zarejestrowanych treningów w ostatnim tygodniu.");
            } else {
                log.info(" -> Znaleziono {} treningów w tym tygodniu:", weeklyTrainings.size());
                for (Training training : weeklyTrainings) {
                    log.info("    * Aktywność: {}, Dystans: {}, Data: {}",
                            training.getActivityType(),
                            training.getDistance(),
                            training.getEndTime());
                }
            }
            log.info("--------------------------------------------------");
        }

        log.info(">>> Koniec generowania raportu <<<");
    }
}