package pl.wsb.fitnesstracker.training.internal;

import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingProvider;
import java.util.Optional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        // Korzystamy z wbudowanej w JPA metody findById
        return trainingRepository.findById(trainingId);
    }

    @Override
    public List<Training> getAllTrainingsForUser(Long userId) {
        return trainingRepository.findByUserId(userId);
    }
}