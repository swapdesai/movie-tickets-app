package au.com.sportsbet.movie.tickets.services.discount;

import au.com.sportsbet.movie.tickets.model.Ticket;
import au.com.sportsbet.movie.tickets.properties.DiscountProperties;
import au.com.sportsbet.movie.tickets.util.CodecUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

@Log4j2
@Component
@RequiredArgsConstructor
public class SeniorDiscount implements Discount<Ticket> {

  public static final double PERCENTAGE_MULTIPLIER = 100;

  private final DiscountProperties discountProperties;

  @Override
  public void applyRule(Ticket ticket) {

    log.debug("ticket={}", CodecUtils.writeValueAsString(ticket));

    ticket.setTotalCost(ticket.getTotalCost().subtract(
        ((ticket.getTotalCost().multiply(BigDecimal.valueOf(discountProperties.getSeniorPercentage()))).divide(BigDecimal.valueOf(PERCENTAGE_MULTIPLIER)))));

  }

}
