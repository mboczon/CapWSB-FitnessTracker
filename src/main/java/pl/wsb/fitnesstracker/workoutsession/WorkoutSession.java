package pl.wsb.fitnesstracker.workoutsession;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import pl.wsb.fitnesstracker.training.api.Training;


@Entity
@Table(name = "Workout_Session")
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "training_id", referencedColumnName = "id")
    private Training training;

    @Column(nullable = false)
    private Long timestamp;

    @Column(nullable = false)
    private double startLatitude;

    @Column(nullable = false)
    private double startLongitude;

    @Column(nullable = false)
    private double endLatitude;

    @Column(nullable = false)
    private double endLongitude;

    @Column
    private double altitude;
}