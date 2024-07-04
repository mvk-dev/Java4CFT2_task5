package task5.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tpp_product_register")
@Getter
@Setter
@AllArgsConstructor
public class TppProductRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private TppRefProductRegisterType type;

    @Column(name = "account")
    private Long account;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "state")
    private String state;

    @Column(name = "account_number")
    private String accountNumber;

    public TppProductRegister() {
    }
}
