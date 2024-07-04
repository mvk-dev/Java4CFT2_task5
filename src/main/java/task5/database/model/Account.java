package task5.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "bussy")
    private Boolean bussy;

    @ManyToOne
    @JoinColumn(name = "account_pool_id", referencedColumnName = "id")
    private AccountPool accountPool;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", bussy=" + bussy +
                ", accountPool=" + accountPool +
                '}';
    }
}
