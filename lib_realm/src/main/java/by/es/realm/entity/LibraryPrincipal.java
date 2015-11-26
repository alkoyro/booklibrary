package by.es.realm.entity;

import java.io.Serializable;
import java.security.Principal;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

import by.es.realm.entity.predefined.Permission;
import by.es.realm.entity.predefined.UserStatus;

@NamedQueries(
        {
                @NamedQuery(name = "findUserByLogin",
                        query = "SELECT OBJECT(user) FROM LibraryPrincipal user WHERE user.login = :login"),
                @NamedQuery(name = "findUserByUuid",
                        query = "SELECT OBJECT(user) from LibraryPrincipal user WHERE user.uuid = :uuid")})
@Entity
@Table(name = "LIB_USER")
public class LibraryPrincipal implements Principal, Serializable
{
    private static final String DEFAULTUserPrincipalName = "user";

    @Id
    @SequenceGenerator(name = "PERSISTENCE_SEQUENCE")
    @GeneratedValue(generator = "PERSISTENCE_SEQUENCE", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "LOGIN", length = 20, nullable = false)
    private String login;

    @Column(name = "PASSWORD", length = 255, nullable = false)
    private String password;

    @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "PERMISSION", joinColumns = {@JoinColumn(name = "LIB_USER_ID")})
    @Enumerated(value = EnumType.STRING)
    private Set<Permission> permissions;

    @Column(name = "FIRST_NAME", length = 20, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 20, nullable = false)
    private String lastName;

    @Column(name = "UUID", unique = true)
    private String uuid;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public Long getId()
    {
        return id;
    }

    public String getLogin()
    {
        return login;
    }

    public String getPassword()
    {
        return password;
    }

    public Set<Permission> getPermissions()
    {
        return permissions;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getUuid()
    {
        return uuid;
    }

    public UserStatus getUserStatus()
    {
        return userStatus;
    }

    @Override
    public String getName()
    {
        return login;
    }

    @Override
    public int hashCode()
    {
        return login.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj instanceof Principal)
        {
            return login.equals(((Principal) obj).getName());
        }
        return false;
    }

    @Override
    public String toString()
    {
        return login.toString();
    }
}
