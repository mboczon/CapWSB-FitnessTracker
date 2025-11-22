package pl.wsb.fitnesstracker.userEvent.api;

import lombok.Getter;
import pl.wsb.fitnesstracker.user.api.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import pl.wsb.fitnesstracker.event.Event;

@Entity
@Table(name = "user_event")
@Getter
public class UserEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    @Column(nullable = false)
    private String status;

    public UserEvent(
            final User user,
            final Event event,
            final String status) {
        this.user = user;
        this.event = event;
        this.status = status;
    }
}