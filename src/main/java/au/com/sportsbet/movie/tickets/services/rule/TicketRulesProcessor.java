package au.com.sportsbet.movie.tickets.services.rule;

import au.com.sportsbet.movie.tickets.model.Ticket;
import au.com.sportsbet.movie.tickets.util.CodecUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class TicketRulesProcessor implements RulesProcessor<Ticket> {

  @Setter
  private List<Rule<Ticket>> rules;

  @Override
  public void processRules(Ticket ticket) {

    log.debug("ticket={}", CodecUtils.writeValueAsString(ticket));

    rules.parallelStream().forEach(rule -> {
      rule.fire(ticket);
    });

  }

}
