package au.com.sportsbet.movie.tickets.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Configuration
@ConfigurationProperties("discount")
@Validated
public class DiscountProperties {

  @NotNull(message="Discount children.percentage property must be set")
  private Integer childrenPercentage;

  @NotNull(message="Discount children.quantity property must be set")
  private Integer childrenQuantity;

  @NotNull(message="Discount senior.percentage property must be set")
  private Integer seniorPercentage;

}

