package by.victoria.app;

import by.victoria.model.dto.TicketDto;

public class CurrentTicket {
    private static CurrentTicket currentTicket;
    private TicketDto ticketDto;

    private CurrentTicket() {
    }

    public static TicketDto getCurrentTicketDto() {
        if (currentTicket == null) {
            currentTicket = new CurrentTicket();
        }
        return currentTicket.ticketDto;
    }

    public static void setCurrentTicketDto(TicketDto ticketDto) {
        if (currentTicket == null) {
            currentTicket = new CurrentTicket();
        }
        currentTicket.ticketDto = ticketDto;
    }
}
