package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BlacklistedTokensModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String uid;
    @Column(length = 500)
    private String token;

    public BlacklistedTokensModel(String token) {
        this.token = token;
    }
}
