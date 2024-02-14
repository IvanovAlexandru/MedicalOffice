package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.RoleEnum;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String uid;
    @Column(unique = true)
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    public UserModel(String username, String password, RoleEnum roleEnum) {
        this.username = username;
        this.password = password;
        this.roleEnum = roleEnum;
    }
}
