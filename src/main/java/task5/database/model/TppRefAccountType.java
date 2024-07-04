package task5.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tpp_ref_account_type")
@Getter
@Setter
public class TppRefAccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long internalId;

    @Column(name = "value", unique = true, nullable = false)
    private String value;
}
