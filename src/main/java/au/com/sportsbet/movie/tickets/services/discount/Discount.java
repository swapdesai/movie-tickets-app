package au.com.sportsbet.movie.tickets.services.discount;

public interface Discount<T> {

  void applyRule(T t);

}
