package unit.au.com.sportsbet.movie.tickets.services;

import au.com.sportsbet.movie.tickets.model.Customer;
import au.com.sportsbet.movie.tickets.model.TransactionIn;
import au.com.sportsbet.movie.tickets.model.TransactionOut;
import au.com.sportsbet.movie.tickets.properties.CostProperties;
import au.com.sportsbet.movie.tickets.properties.DiscountProperties;
import au.com.sportsbet.movie.tickets.services.TransactionService;
import au.com.sportsbet.movie.tickets.services.discount.ChildrenDiscount;
import au.com.sportsbet.movie.tickets.services.discount.SeniorDiscount;
import au.com.sportsbet.movie.tickets.services.rule.DiscountRule;
import au.com.sportsbet.movie.tickets.services.rule.TicketRulesProcessor;

import au.com.sportsbet.movie.tickets.util.CodecUtils;

import org.apache.commons.io.IOUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.skyscreamer.jsonassert.JSONAssert;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class TransactionServiceTest {

  private TransactionService transactionService;

  @BeforeEach
  void setup() {

    CostProperties costProperties = new CostProperties();
    costProperties.setAdult(BigDecimal.valueOf(25.00));
    costProperties.setTeen(BigDecimal.valueOf(12.00));
    costProperties.setChildren(BigDecimal.valueOf(5.00));

    DiscountProperties discountProperties = new DiscountProperties();
    discountProperties.setSeniorPercentage(30);;
    discountProperties.setChildrenPercentage(25);
    discountProperties.setChildrenQuantity(3);

    SeniorDiscount seniorDiscount = new SeniorDiscount(discountProperties);
    ChildrenDiscount childrenDiscount = new ChildrenDiscount(discountProperties);
    DiscountRule discountRule = new DiscountRule(seniorDiscount, childrenDiscount);

    TicketRulesProcessor rulesProcessor = new TicketRulesProcessor();
    rulesProcessor.setRules(
        Arrays.asList(discountRule)
    );

    transactionService = new TransactionService(costProperties, rulesProcessor);

  }

  @Test
  @DisplayName("Scenario 1: 2 Children and 1 Senior")
  void shouldApplyChildrenAndSeniorRule() throws Exception {

    Customer customer1 = new Customer();
    customer1.setName("John Smith");
    customer1.setAge(70);

    Customer customer2 = new Customer();
    customer2.setName("Jane Doe");
    customer2.setAge(5);

    Customer customer3 = new Customer();
    customer3.setName("Bob Doe");
    customer3.setAge(6);

    TransactionIn transactionIn = new TransactionIn();
    transactionIn.setTransactionId(1L);
    transactionIn.setCustomers(new ArrayList<>(Arrays.asList(
        customer1, customer2, customer3
    )));

    TransactionOut transactionOut = transactionService.processTransaction(transactionIn);

    String expected = IOUtils.toString(TransactionServiceTest.class.getResourceAsStream("/data/children-and-senior.json"),
        StandardCharsets.UTF_8);

    JSONAssert.assertEquals(expected, CodecUtils.writeValueAsString(transactionOut), true);

  }

  @Test
  @DisplayName("Scenario 2: 1 Adult, 3 Children and 1 Teen")
  void shouldApplyAdultChildrenAndTeenRule() throws Exception {

    Customer customer1 = new Customer();
    customer1.setName("Billy Kidd");
    customer1.setAge(36);

    Customer customer2 = new Customer();
    customer2.setName("Zoe Daniels");
    customer2.setAge(3);

    Customer customer3 = new Customer();
    customer3.setName("George White");
    customer3.setAge(8);

    Customer customer4 = new Customer();
    customer4.setName("Tommy Anderson");
    customer4.setAge(9);

    Customer customer5 = new Customer();
    customer5.setName("Joe Smith");
    customer5.setAge(17);

    TransactionIn transactionIn = new TransactionIn();
    transactionIn.setTransactionId(2L);
    transactionIn.setCustomers(new ArrayList<>(Arrays.asList(
        customer1, customer2, customer3, customer4, customer5
    )));

    TransactionOut transactionOut = transactionService.processTransaction(transactionIn);

    String expected = IOUtils.toString(TransactionServiceTest.class.getResourceAsStream("/data/adult-children-and-teen.json"),
        StandardCharsets.UTF_8);

    JSONAssert.assertEquals(expected, CodecUtils.writeValueAsString(transactionOut), true);

  }

  @Test
  @DisplayName("Scenario 3: 1 Adult, 1 Children, 1 Senior and 1 Teen")
  void shouldApplyAdultChildrenSeniorAndTeenRule() throws Exception {

    Customer customer1 = new Customer();
    customer1.setName("Jesse James");
    customer1.setAge(36);

    Customer customer2 = new Customer();
    customer2.setName("Daniel Anderson");
    customer2.setAge(95);

    Customer customer3 = new Customer();
    customer3.setName("Mary Jones");
    customer3.setAge(15);

    Customer customer4 = new Customer();
    customer4.setName("Tommy Anderson");
    customer4.setAge(10);

    TransactionIn transactionIn = new TransactionIn();
    transactionIn.setTransactionId(3L);
    transactionIn.setCustomers(new ArrayList<>(Arrays.asList(
        customer1, customer2, customer3, customer4
    )));

    TransactionOut transactionOut = transactionService.processTransaction(transactionIn);

    String expected = IOUtils.toString(TransactionServiceTest.class.getResourceAsStream("/data/adult-children-senior-and-teen.json"),
        StandardCharsets.UTF_8);

    JSONAssert.assertEquals(expected, CodecUtils.writeValueAsString(transactionOut), true);

  }

}
