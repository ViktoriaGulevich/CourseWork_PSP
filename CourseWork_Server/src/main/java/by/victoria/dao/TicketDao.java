package by.victoria.dao;

import by.victoria.model.entity.Status;
import by.victoria.model.entity.Ticket;
import by.victoria.model.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketDao {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();


    public List<Ticket> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<Ticket> tickets = session.createQuery("select t from Ticket t", Ticket.class).getResultList();

        transaction.commit();
        session.close();

        return tickets;
    }

    public Ticket bookTicket(User user, Ticket ticket) {
        return addTicket(user, ticket, "забронирован");
    }

    public Ticket buyTicket(User user, Ticket ticket) {
        return addTicket(user, ticket, "куплен");
    }

    public List<Ticket> findAllBoughtTickets(Integer userId) {
        return findAllByUserIdAndStatus(userId, "куплен");
    }

    public List<Ticket> findAllBookedTickets(Integer userId) {
        return findAllByUserIdAndStatus(userId, "забронирован");
    }

    public Ticket buyTicketAfterBooking(Ticket ticket) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

         ticket = session.createQuery("""
                select t
                from Ticket t
                    join fetch t.flight f
                where t.numberOfSeat = :numberOfSeat and f.fromWhere = :fromWhere and f.toWhere = :toWhere
                """, Ticket.class)
                .setParameter("numberOfSeat", ticket.getNumberOfSeat())
                .setParameter("fromWhere", ticket.getFlight().getFromWhere())
                .setParameter("toWhere", ticket.getFlight().getToWhere())
                .getSingleResult();

        Status status = session.createQuery("""
                select s
                from Status s
                where s.name = 'куплен'
                """, Status.class).getSingleResult();

        ticket.setStatuses(Set.of(status));

        session.update(ticket);

        transaction.commit();
        session.close();

        return ticket;
    }

    public void delete(User user, Ticket ticket) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        UserDao userDao = new UserDao();
        user = userDao.find(user.getUsername(), user.getPassword());
        System.out.println(ticket);
        Set<Ticket> ticketSet = user.getTickets()
                .stream()
                .filter(t ->
                        !(t.getNumberOfSeat().equals(ticket.getNumberOfSeat())
                                && t.getFlight().getToWhere().equals(ticket.getFlight().getToWhere())
                                && t.getFlight().getFromWhere().equals(ticket.getFlight().getFromWhere())
                        )
                )
                .collect(Collectors.toSet());
        user.setTickets(ticketSet);
        userDao.update(user);
        transaction.commit();
        session.close();
    }

    private List<Ticket> findAllByUserIdAndStatus(Integer userId, String status) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<Ticket> ticketList = (List<Ticket>) session.createQuery("""
                select t
                from User u
                    join fetch u.tickets t
                    join fetch t.statuses s
                where u.id = :id and s.name = :status
                """)
                .setParameter("id", userId)
                .setParameter("status", status)
                .getResultStream()
                .flatMap(o -> ((Set<Ticket>) o).stream())
                .distinct()
                .collect(Collectors.toList());

        transaction.commit();
        session.close();

        return ticketList;
    }

    private Ticket addTicket(User user, Ticket ticket, String status) {
        boolean isFindPlace = addPlace(ticket);
        if (isFindPlace) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            Status expectedStatus = session.createQuery("""
                    select s from Status s where s.name = :status
                    """, Status.class)
                    .setParameter("status", status)
                    .getSingleResult();
            ticket.setStatuses(Set.of(expectedStatus));
            Integer ticketId = (Integer) session.save(ticket);

            transaction.commit();
            session.close();

            ticket = find(ticketId);
            UserDao userDao = new UserDao();
            user = userDao.find(user.getUsername(), user.getPassword());
            user.getTickets().add(ticket);
            userDao.update(user);
        }

        return ticket;
    }

    private Ticket find(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Ticket ticket = null;
        try {
            ticket = session.createQuery("""
                    select t
                    from Ticket t where t.id = :id
                    """, Ticket.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        transaction.commit();
        session.close();

        return ticket;
    }

    private boolean addPlace(Ticket ticket) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<Integer> listPlaces = session.createQuery("""
                select t.numberOfSeat
                from Ticket t join t.flight f
                where f.id = :id
                """, Integer.class)
                .setParameter("id", ticket.getFlight().getId())
                .getResultList();

        for (int i = 0; i < ticket.getFlight().getCountOfSeats(); i++) {
            if (!listPlaces.contains(i)) {
                ticket.setNumberOfSeat(i);
                return true;
            }
        }
        transaction.commit();
        session.close();

        return false;
    }
}
