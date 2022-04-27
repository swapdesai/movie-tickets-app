package au.com.sportsbet.movie.tickets.services.discount;

import au.com.sportsbet.movie.tickets.model.Ticket;
import au.com.sportsbet.movie.tickets.properties.DiscountProperties;
import au.com.sportsbet.movie.tickets.util.CodecUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Log4j2
@Component
@RequiredArgsConstructor
public class ChildrenDiscount implements Discount<Ticket> {

  private final DiscountProperties discountProperties;

  public static final double PERCENTAGE_MULTIPLIER = 100;

  @Override
  public void applyRule(Ticket ticket) {

    log.debug("ticket={}", CodecUtils.writeValueAsString(ticket));

    if (ticket.getQuantity() >= discountProperties.getChildrenQuantity()) {
      ticket.setTotalCost(ticket.getTotalCost().subtract(
          (ticket.getTotalCost().multiply(BigDecimal.valueOf(discountProperties.getChildrenPercentage()))).divide(BigDecimal.valueOf(PERCENTAGE_MULTIPLIER))));
    }

  }

}
