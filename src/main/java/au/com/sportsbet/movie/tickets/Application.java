package au.com.sportsbet.movie.tickets;

import au.com.sportsbet.movie.tickets.services.rule.DiscountRule;
import au.com.sportsbet.movie.tickets.services.rule.TicketRulesProcessor;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@Log4j2
@SpringBootApplication
public class Application implements ApplicationRunner {

  @Autowired
  private ApplicationContext context;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("Started Sportsbet Movie Tickets Application");

    // setup rules engine
    setup();

  }

  public final void setup() {
    // create rules processor
    TicketRulesProcessor ticketRulesProcessor =
        (TicketRulesProcessor) context.getBean("ticketRulesProcessor");

    // create discount rules beans
    DiscountRule discountRule = (DiscountRule) context.getBean("discountRule");

    // add rules beans to the rules processor
    ticketRulesProcessor.setRules(
        Arrays.asList(discountRule)
    );
  }

}
