package by.es.ejb.entity;

import by.es.realm.entity.predefined.Permission;
import by.es.realm.entity.predefined.UserStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "LIB_USER")
@NamedQueries({
        @NamedQuery(name = "findUserByLoginAndPassword",
                query = "SELECT OBJECT(user) FROM User user WHERE user.login = :login AND user.password = :password"),
        @NamedQuery(name = "findUserCountByLogin",
                query = "SELECT count(user) FROM User user WHERE user.login = :login"),
        @NamedQuery(name = "findUserByLogin",
                query = "SELECT user FROM User user WHERE user.login = :login"),
        @NamedQuery(name = "findUserByEmail",
                query = "SELECT user FROM User user WHERE user.email = :email")
})

public class User extends PersistenceEntity {

    @Column(name = "LOGIN", length = 25, nullable = false, unique = true)
    private String login;

    /**
     * contains password hash
     */
    @Column(name = "PASSWORD", length = 255, nullable = false)
    private String password;

    @Column(name = "FIRST_NAME", length = 25, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 25, nullable = false)
    private String lastName;

    @Column(name = "EMAIL", length = 25, nullable = false)
    private String email;

    @Column(name = "DISCOUNT", length = 25, nullable = false)
    private double discount;

    @Column(name = "LAST_LOGIN_DATE")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastLoginDate;

    @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "PERMISSION", joinColumns = {@JoinColumn(name = "LIB_USER_ID")})
    @Enumerated(value = EnumType.STRING)
    private Set<Permission> permissions;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.NORMAL;

    @Column(name = "UUID")
    private String uuid;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }


    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}
