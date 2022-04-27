package au.com.sportsbet.movie.tickets.services.rule;

public interface RulesProcessor<T> {
  void processRules(T t);
}
