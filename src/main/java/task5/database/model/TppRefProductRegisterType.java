package task5.database.model;


import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tpp_ref_product_register_type")
@Getter
public class TppRefProductRegisterType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long internalId;

    @Column(name = "value", unique = true, nullable = false)
    private String value;

    @Column(name = "register_type_name", nullable = false)
    private String registerTypeName;

    @Column(name = "register_type_start_date")
    private LocalDateTime registerTypeStartDate;

    @Column(name = "register_type_end_date")
    private LocalDateTime registerTypeEndDate;

    @ManyToOne
    @JoinColumn(name = "product_class_id", nullable = false, referencedColumnName = "id"/*, insertable = false, updatable = false*/)
    private TppRefProductClass productClass;

    @ManyToOne
    @JoinColumn(name = "account_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TppRefAccountType accountTypeEntity;
}
