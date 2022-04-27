package au.com.sportsbet.movie.tickets.controllers;

import au.com.sportsbet.movie.tickets.api.TransactionApi;
import au.com.sportsbet.movie.tickets.model.TransactionIn;
import au.com.sportsbet.movie.tickets.model.TransactionOut;

import au.com.sportsbet.movie.tickets.services.TransactionService;
import au.com.sportsbet.movie.tickets.util.CodecUtils;
import au.com.sportsbet.movie.tickets.validation.TransactionInValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class TransactionApiController implements TransactionApi {

  private final TransactionInValidator transactionInValidator;

  private final TransactionService transactionService;

  @Override
  public ResponseEntity<TransactionOut> postTransaction(TransactionIn transactionIn) {

    log.info("transactionIn.transactionId={}", CodecUtils.writeValueAsString(transactionIn.getTransactionId()));
    log.debug("transactionIn={}", CodecUtils.writeValueAsString(transactionIn));

    // Request validation
    if (!transactionInValidator.isValid(transactionIn)) {

      log.error("Invalid request. transactionId={}", transactionIn.getTransactionId());

      return ResponseEntity.badRequest().build();

    }

    TransactionOut transactionOut = transactionService.processTransaction(transactionIn);
    // TODO: Response validation!

    log.debug("transactionOut={}", CodecUtils.writeValueAsString(transactionOut));
    log.info("transactionOut.transactionId={}", CodecUtils.writeValueAsString(transactionOut.getTransactionId()));

    return ResponseEntity.ok().body(transactionOut);

  }

}
