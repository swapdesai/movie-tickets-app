package unit.au.com.sportsbet.movie.tickets.services.discount;

import au.com.sportsbet.movie.tickets.model.Ticket;
import au.com.sportsbet.movie.tickets.properties.DiscountProperties;
import au.com.sportsbet.movie.tickets.services.discount.SeniorDiscount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.util.Assert;

import java.math.BigDecimal;

class SeniorDiscountTest {

  private SeniorDiscount seniorDiscount;

  @BeforeEach
  void setup() {

    DiscountProperties discountProperties = new DiscountProperties();
    discountProperties.setSeniorPercentage(30);

    seniorDiscount = new SeniorDiscount(discountProperties);

  }

  @Test
  @DisplayName("Scenario 1: Should apply 30 percentage discount for seniors")
  void shouldApply25PercentageRule() {

    Ticket ticket = new Ticket();
    ticket.setTotalCost(BigDecimal.valueOf(25.00));

    seniorDiscount.applyRule(ticket);

    Assert.isTrue(ticket.getTotalCost().equals(BigDecimal.valueOf(17.50)), "");

  }

}