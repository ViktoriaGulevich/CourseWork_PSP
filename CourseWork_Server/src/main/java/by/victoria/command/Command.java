package by.victoria.command;

import java.io.Serializable;

public enum Command implements Serializable {
    LOGIN,
    REGISTRATION,

    BUY_TICKET,
    BUY_TICKET_AFTER_BOOKING,

    FIND_ALL_TICKETS,
    FIND_ALL_BOUGHT_TICKETS,
    FIND_ALL_BOOKED_TICKETS,

    BOOK_TICKET,

    CANCEL_RESERVATION_TICKET,
    RETURN_TICKET,

    FIND_FLIGHT,
    FIND_FLIGHT_USE_PARAMS,
    FIND_ALL_FLIGHTS,
    FIND_ALL_FREE_PLACES,

    ADD_FLIGHT,
    EDIT_FLIGHT,
    EXIT,

    USER,
    ADMIN,
    UNAUTHORIZED
}
