package au.com.sportsbet.movie.tickets.services.rule;

public interface Rule<T> {
  void fire(T t);
}

