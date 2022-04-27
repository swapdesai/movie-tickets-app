package au.com.sportsbet.movie.tickets.services.rule;

import au.com.sportsbet.movie.tickets.model.Ticket;
import au.com.sportsbet.movie.tickets.services.discount.ChildrenDiscount;

import au.com.sportsbet.movie.tickets.services.discount.SeniorDiscount;
import au.com.sportsbet.movie.tickets.util.CodecUtils;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class DiscountRule implements Rule<Ticket> {

  private final SeniorDiscount seniorDiscount;

  private final ChildrenDiscount childrenDiscount;

  @SuppressWarnings("DuplicateBranchesInSwitch")
  @Override
  public void fire(Ticket ticket) {

    log.debug("ticket={}", CodecUtils.writeValueAsString(ticket));

    switch (ticket.getTicketType()) {
      case ADULT:
        break;
      case SENIOR:
        seniorDiscount.applyRule(ticket);
        break;
      case TEEN:
        break;
      case CHILDREN:
        childrenDiscount.applyRule(ticket);
        break;
      default:
        break;
    }

  }

}
