package unit.au.com.sportsbet.movie.tickets.services.discount;

import au.com.sportsbet.movie.tickets.model.Ticket;
import au.com.sportsbet.movie.tickets.properties.DiscountProperties;
import au.com.sportsbet.movie.tickets.services.discount.ChildrenDiscount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;

class ChildrenDiscountTest {

  private ChildrenDiscount childrenDiscount;

  @BeforeEach
  void setup() {

    DiscountProperties discountProperties = new DiscountProperties();
    discountProperties.setChildrenPercentage(25);
    discountProperties.setChildrenQuantity(3);

    childrenDiscount = new ChildrenDiscount(discountProperties);

  }

  @Test
  @DisplayName("Scenario 1: Should apply 25 percentage discount when there are 3 children")
  void shouldApply25PercentageRule() {

    Ticket ticket = new Ticket();
    ticket.setQuantity(3);
    ticket.setTotalCost(BigDecimal.valueOf(15.00));

    childrenDiscount.applyRule(ticket);

    Assert.isTrue(ticket.getTotalCost().equals(BigDecimal.valueOf(11.25)), "");

  }

  @Test
  @DisplayName("Scenario 2: Should not apply 25 percentage discount when there are less than 3 children")
  void shouldNotApply25PercentageRule() {

    Ticket ticket = new Ticket();
    ticket.setQuantity(2);
    ticket.setTotalCost(BigDecimal.valueOf(10.00));

    childrenDiscount.applyRule(ticket);

    Assert.isTrue(ticket.getTotalCost().equals(BigDecimal.valueOf(10.00)), "");

  }

}