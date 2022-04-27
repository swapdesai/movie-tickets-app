package au.com.sportsbet.movie.tickets.properties;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

@Data
@Configuration
@ConfigurationProperties("cost")
@Validated
public class CostProperties {

  @NotNull(message="Cost adult property must be set")
  private BigDecimal adult;

  @NotNull(message="Cost teen property must be set")
  private BigDecimal teen;

  @NotNull(message="Cost children property must be set")
  private BigDecimal children;

}
