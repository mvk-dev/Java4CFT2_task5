package task5.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tpp_product")
@Getter
@Setter
@AllArgsConstructor
public class TppProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_code_id")
    private Long productCodeId;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "type")
    private String type;

    @Column(name = "number")
    private String number;

    @Column(name = "priority")
    private Long priority;

    @Column(name = "date_of_conclusion")
    private LocalDate dateOfConclusion;

    @Column(name = "start_date_time")
    private LocalDate startDateTime;

    @Column(name = "end_date_time")
    private LocalDate endDateTime;

    @Column(name = "days")
    private Long days;

    @Column(name = "penalty_rate")
    private BigDecimal penaltyRate;

    @Column(name = "nso")
    private BigDecimal nso;

    @Column(name = "threshold_amount")
    private BigDecimal thresholdAmount;

    @Column(name = "requisite_type")
    private String requisiteType;

    @Column(name = "interest_rate_type")
    private String interestRateType;

    @Column(name = "tax_rate")
    private BigDecimal taxRate;

    @Column(name = "reasone_close")
    private String reasoneClose;

    @Column(name = "state")
    private String state;

    public TppProduct() {
    }
}
