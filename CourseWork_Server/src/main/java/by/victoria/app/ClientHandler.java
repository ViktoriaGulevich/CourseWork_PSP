package by.victoria.app;

import by.victoria.command.Command;
import by.victoria.connection.ConnectionHandler;
import by.victoria.dao.FlightDao;
import by.victoria.dao.TicketDao;
import by.victoria.dao.UserDao;
import by.victoria.mapper.FlightMapper;
import by.victoria.mapper.TicketMapper;
import by.victoria.mapper.UserMapper;
import by.victoria.model.dto.FlightDto;
import by.victoria.model.dto.FreePlaceDto;
import by.victoria.model.dto.TicketDto;
import by.victoria.model.dto.UserDto;
import by.victoria.model.entity.Flight;
import by.victoria.model.entity.Role;
import by.victoria.model.entity.Ticket;
import by.victoria.model.entity.User;

import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

import static by.victoria.command.Command.*;

public class ClientHandler implements Runnable {
    private final ConnectionHandler connectionHandler;

    public ClientHandler(Socket socket) {
        this.connectionHandler = new ConnectionHandler(socket);
    }

    @Override
    public void run() {
        UserDao userDao = new UserDao();
        TicketDao ticketDao = new TicketDao();
        FlightDao flightDao = new FlightDao();

        UserMapper userMapper = new UserMapper();
        TicketMapper ticketMapper = new TicketMapper();
        FlightMapper flightMapper = new FlightMapper();

        while (true) {
            Command command;
            try {
                command = (Command) connectionHandler.readObject();
            } catch (RuntimeException e) {
                command = EXIT;
            }
            System.out.println(command);
            switch (command) {
                case LOGIN -> {
                    UserDto userDto = (UserDto) connectionHandler.readObject();

                    User user = userDao.find(userDto.getUsername(), userDto.getPassword());
                    if (user == null) {
                        connectionHandler.writeObject(UNAUTHORIZED);
                    } else {
                        List<String> roles = user.getRoles()
                                .stream()
                                .map(Role::getName)
                                .collect(Collectors.toList());
                        if (roles.contains("USER")) {
                            connectionHandler.writeObject(USER);
                        } else {
                            connectionHandler.writeObject(ADMIN);
                        }
                        connectionHandler.writeObject(userMapper.userToUserDto(user));
                    }
                }
                case REGISTRATION -> {
                    UserDto userDto = (UserDto) connectionHandler.readObject();
                    User user = userMapper.userDtoToUser(userDto);
                   connectionHandler.writeObject( userDao.create(user));
                }
                case BUY_TICKET -> {
                    UserDto userDto = (UserDto) connectionHandler.readObject();
                    FlightDto flightDto = (FlightDto) connectionHandler.readObject();

                    User user = userMapper.userDtoToUser(userDto);
                    Flight flight = flightMapper.flightDtoToFlight(flightDto);

                    Ticket ticket = new Ticket(flight, flight.getCost());
                    ticket = ticketDao.buyTicket(user, ticket);
                    TicketDto ticketDto = ticketMapper.ticketToTicketDto(ticket);
                    connectionHandler.writeObject(ticketDto);
                }
                case BUY_TICKET_AFTER_BOOKING -> {
                    TicketDto ticketDto = (TicketDto) connectionHandler.readObject();
                    Ticket ticket = ticketMapper.ticketDtoToTicket(ticketDto);
                    ticket = ticketDao.buyTicketAfterBooking(ticket);
                    ticketDto = ticketMapper.ticketToTicketDto(ticket);
                    connectionHandler.writeObject(ticketDto);
                }
                case FIND_ALL_TICKETS -> {
                    List<TicketDto> ticketDtoList = ticketMapper.ticketListToTicketDtoList(ticketDao.findAll());
                    connectionHandler.writeObject(ticketDtoList);
                }
                case FIND_ALL_BOUGHT_TICKETS -> {
                    Integer userId = (Integer) connectionHandler.readObject();
                    List<TicketDto> ticketDtoList = ticketMapper
                            .ticketListToTicketDtoList(ticketDao.findAllBoughtTickets(userId));
                    connectionHandler.writeObject(ticketDtoList);
                }
                case FIND_ALL_BOOKED_TICKETS -> {
                    Integer userId = (Integer) connectionHandler.readObject();
                    List<TicketDto> ticketDtoList = ticketMapper
                            .ticketListToTicketDtoList(ticketDao.findAllBookedTickets(userId));
                    connectionHandler.writeObject(ticketDtoList);
                }
                case BOOK_TICKET -> {
                    UserDto userDto = (UserDto) connectionHandler.readObject();
                    FlightDto flightDto = (FlightDto) connectionHandler.readObject();

                    User user = userMapper.userDtoToUser(userDto);
                    Flight flight = flightMapper.flightDtoToFlight(flightDto);

                    Ticket ticket = new Ticket(flight, flight.getCost());
                    ticket = ticketDao.bookTicket(user, ticket);
                    TicketDto ticketDto = ticketMapper.ticketToTicketDto(ticket);
                    connectionHandler.writeObject(ticketDto);
                }
                case CANCEL_RESERVATION_TICKET, RETURN_TICKET -> {
                    TicketDto ticketDto = (TicketDto) connectionHandler.readObject();
                    UserDto userDto = (UserDto) connectionHandler.readObject();

                    User user = userMapper.userDtoToUser(userDto);
                    Ticket ticket = ticketMapper.ticketDtoToTicket(ticketDto);
                    ticketDao.delete(user, ticket);
                }
                case FIND_FLIGHT -> {
                    Integer id = (Integer) connectionHandler.readObject();
                    Flight flight = flightDao.find(id);
                    FlightDto flightDto = flightMapper.flightToFlightDto(flight);
                    connectionHandler.writeObject(flightDto);
                }
                case FIND_FLIGHT_USE_PARAMS -> {
                    FlightDto flightDto = (FlightDto) connectionHandler.readObject();
                    Flight flight = flightMapper.flightDtoToFlight(flightDto);
                    if (flight.getDepartureDate() != null) {
                        flight = flightDao.findByFromWhereAndToWhereAndDepartureDate(flight);
                        flightDto = flightMapper.flightToFlightDto(flight);
                        connectionHandler.writeObject(flightDto);
                    } else {
                    List<Flight> flights=flightDao.findByFromWhereAndToWhere(flight);
                        List<FlightDto> flightDtos = flightMapper.flightListToFlightDtoList(flights);
                        connectionHandler.writeObject(flightDtos);
                    }
                }
                case FIND_ALL_FLIGHTS -> {
                    List<FlightDto> flightDtoList = flightMapper.flightListToFlightDtoList(flightDao.findAll());
                    connectionHandler.writeObject(flightDtoList);
                }
                case FIND_ALL_FREE_PLACES -> {
                    List<Ticket> ticketList = ticketDao.findAll();
                    List<FreePlaceDto> result = ticketMapper.ticketListToFreePlaceDtoList(ticketList);
                    connectionHandler.writeObject(result);
                }
                case ADD_FLIGHT -> {
                    FlightDto flightDto = (FlightDto) connectionHandler.readObject();
                    Flight flight = flightMapper.flightDtoToFlight(flightDto);
                    flightDao.create(flight);
                }
                case EDIT_FLIGHT -> {
                    FlightDto flightDto = (FlightDto) connectionHandler.readObject();
                    Flight flight = flightMapper.flightDtoToFlight(flightDto);
                    flightDao.update(flight);
                }
                case EXIT -> {
                    connectionHandler.close();
                    return;
                }
            }
        }
    }
}
