package by.victoria.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ticket_statuses",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id")
    )
    private Set<Status> statuses;

    @Transient
    @ManyToMany(mappedBy = "tickets")
    private Set<User> users;

    @ManyToOne
    @JoinColumn
    private Flight flight;

    @Column(name = "number_of_seat")
    private Integer numberOfSeat;

    @Column(name = "cost")
    private Double cost;

    public Ticket(Flight flight, Double cost) {
        this.flight = flight;
        this.cost = cost;
    }
}
