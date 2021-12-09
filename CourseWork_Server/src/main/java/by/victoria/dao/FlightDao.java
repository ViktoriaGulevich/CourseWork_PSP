package by.victoria.dao;

import by.victoria.model.entity.Flight;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class FlightDao {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();


    public List<Flight> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<Flight> flightList = session.createQuery("""
                        select f
                        from Flight f
                        """, Flight.class)
                .getResultList();

        transaction.commit();
        session.close();

        return flightList;
    }

    public void update(Flight flight) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.update(flight);

        transaction.commit();
        session.close();
    }

    public Flight find(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Flight flight = null;
        try {
            flight = session.createQuery("""
                            select f
                            from Flight f where f.id = :id
                            """, Flight.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        transaction.commit();
        session.close();

        return flight;
    }

    public Flight findByFromWhereAndToWhereAndDepartureDate(Flight flight) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Flight singleResult = null;
        try {
            singleResult = session.createQuery("""
                            select f
                            from Flight f
                            where f.fromWhere = :fromWhere and f.toWhere = :toWhere and f.departureDate between :startDate and :finishDate
                            """, Flight.class)
                    .setParameter("fromWhere", flight.getFromWhere())
                    .setParameter("toWhere", flight.getToWhere())
                    .setParameter("startDate", flight.getDepartureDate())
                    .setParameter("finishDate", flight.getDepartureDate().plusDays(1))
                    .getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        transaction.commit();
        session.close();

        return singleResult;
    }

    public void create(Flight flight) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(flight);

        transaction.commit();
        session.close();
    }

    public List<Flight> findByFromWhereAndToWhere(Flight flight) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

List<Flight>flightList=new ArrayList<>();
        try {
            flightList = session.createQuery("""
                            select f
                            from Flight f
                            where f.fromWhere = :fromWhere and f.toWhere = :toWhere
                            """, Flight.class)
                    .setParameter("fromWhere", flight.getFromWhere())
                    .setParameter("toWhere", flight.getToWhere())
                    .getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        transaction.commit();
        session.close();

        return flightList;
    }
}
