package au.com.sportsbet.movie.tickets.validation;

import au.com.sportsbet.movie.tickets.model.Customer;
import au.com.sportsbet.movie.tickets.model.TransactionIn;
import org.springframework.stereotype.Component;

@Component
public class TransactionInValidator {

  public boolean isValid(TransactionIn transactionIn) {

    if (transactionIn.getCustomers().size() == 0) {
      return false;
    }

    for (Customer customer: transactionIn.getCustomers()) {
      if (customer.getAge() < 0) { // INFO: -ve ages are not allowed
        return false;
      }
    }

    return true;

  }

}
