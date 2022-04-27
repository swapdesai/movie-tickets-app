package au.com.sportsbet.movie.tickets.services;

import au.com.sportsbet.movie.tickets.model.Customer;
import au.com.sportsbet.movie.tickets.model.Ticket;
import au.com.sportsbet.movie.tickets.model.TransactionIn;
import au.com.sportsbet.movie.tickets.model.TransactionOut;
import au.com.sportsbet.movie.tickets.properties.CostProperties;
import au.com.sportsbet.movie.tickets.services.rule.RulesProcessor;

import au.com.sportsbet.movie.tickets.util.CodecUtils;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static au.com.sportsbet.movie.tickets.model.Ticket.TicketTypeEnum.*;

@Log4j2
@RequiredArgsConstructor
@Service
public class TransactionService {

  private final CostProperties costProperties;

  private final RulesProcessor rulesProcessor;

  @SuppressWarnings({"DuplicatedCode", "unchecked"})
  public TransactionOut processTransaction(TransactionIn transactionIn) {

    log.debug("transactionIn={}", CodecUtils.writeValueAsString(transactionIn));

    // Set Transaction Id
    TransactionOut transactionOut = new TransactionOut();
    transactionOut.setTransactionId(transactionIn.getTransactionId());

    // Build Ticket array
    List<Ticket> ticketList = new ArrayList<>();
    for (Customer customer: transactionIn.getCustomers()) {
       switch (getType(customer)) {
        case ADULT:
          Optional<Ticket> optionalAdultTypeTicket = ticketList.parallelStream()
              .filter(t -> ADULT.equals(t.getTicketType()))
              .findFirst();

          if (optionalAdultTypeTicket.isPresent()) {
            updateTicket(optionalAdultTypeTicket.get(), costProperties.getAdult());
          } else {
            ticketList.add(createTicket(ADULT, costProperties.getAdult()));
          }
          break;

        case SENIOR:
          Optional<Ticket> optionalSeniorTypeTicket = ticketList.parallelStream()
              .filter(t -> SENIOR.equals(t.getTicketType()))
              .findFirst();

          if (optionalSeniorTypeTicket.isPresent()) {
            updateTicket(optionalSeniorTypeTicket.get(), costProperties.getAdult());
          } else {
            ticketList.add(createTicket(SENIOR, costProperties.getAdult()));
          }
          break;

        case TEEN:
          Optional<Ticket> optionalTeenTypeTicket = ticketList.parallelStream()
              .filter(t -> TEEN.equals(t.getTicketType()))
              .findFirst();

          if (optionalTeenTypeTicket.isPresent()) {
            updateTicket(optionalTeenTypeTicket.get(), costProperties.getTeen());
          } else {
            ticketList.add(createTicket(TEEN, costProperties.getTeen()));
          }
          break;

        case CHILDREN:
          Optional<Ticket> optionalChildrenTypeTicket = ticketList.parallelStream()
              .filter(t -> CHILDREN.equals(t.getTicketType()))
              .findFirst();

          if (optionalChildrenTypeTicket.isPresent()) {
            updateTicket(optionalChildrenTypeTicket.get(), costProperties.getChildren());
          } else {
            ticketList.add(createTicket(CHILDREN, costProperties.getChildren()));
          }
          break;
      }
    }

    // Fire Discount Rules
    for (Ticket ticket: ticketList) {
      rulesProcessor.processRules(ticket);
    }

    // Order Alphabetically
    Collections.sort(ticketList, Comparator.comparing(o -> o.getTicketType().toString()));

    transactionOut.setTickets(ticketList);

    // Build Transaction Cost
    transactionOut.setTotalCost(transactionOut.getTickets().parallelStream()
        .map(Ticket::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add));

    log.debug("transactionOut={}", CodecUtils.writeValueAsString(transactionOut));

    return transactionOut;

  }

  private Ticket createTicket(Ticket.TicketTypeEnum ticketType, BigDecimal cost) {

    Ticket ticket = new Ticket();
    ticket.setTicketType(ticketType);
    ticket.setQuantity(1);
    ticket.setTotalCost(cost);

    return ticket;

  }

  private void updateTicket(Ticket ticket, BigDecimal cost) {

    ticket.setQuantity(ticket.getQuantity() + 1);
    ticket.setTotalCost(ticket.getTotalCost().add(cost));

  }

  private Ticket.TicketTypeEnum getType(Customer customer) {

    if (customer.getAge() >= 18 && customer.getAge() < 65) {
      return ADULT;
    } else if (customer.getAge() >= 65) {
      return SENIOR;
    } else if (customer.getAge() >= 11 && customer.getAge() < 18) {
      return TEEN;
    } else {
      return CHILDREN;
    }

  }

}
