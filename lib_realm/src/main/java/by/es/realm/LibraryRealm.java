package by.es.realm;

import java.util.*;

import javax.persistence.Query;


import by.es.realm.entity.predefined.UserStatus;
import org.apache.log4j.Logger;
import org.jvnet.hk2.annotations.Service;

import by.es.realm.entity.LibraryPrincipal;
import by.es.realm.entity.dao.EntityManagerProvider;
import by.es.realm.entity.predefined.Permission;

import com.sun.appserv.security.AppservRealm;
import com.sun.enterprise.security.auth.realm.BadRealmException;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchRealmException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;

@Service(name = "LibraryRealm")
public class LibraryRealm extends AppservRealm
{
    public static final String AUTH_TYPE = "jdbcLibrary";
    private static final Logger LOGGER = Logger.getLogger(LibraryRealm.class);
    private LibraryPrincipal principal;


    @Override
    protected void init(Properties props) throws BadRealmException,
            NoSuchRealmException
    {
        super.init(props);
        String jaasCtx = props.getProperty(AppservRealm.JAAS_CONTEXT_PARAM);

        LOGGER.debug("Initializing realm...");

        if (jaasCtx == null)
        {
            String message = "No " + JAAS_CONTEXT_PARAM + " defind";
            LOGGER.error(message);
            throw new BadRealmException(message);
        }

        this.setProperty(AppservRealm.JAAS_CONTEXT_PARAM, jaasCtx);
    }

    @Override
    public String getAuthType()
    {
        return AUTH_TYPE;
    }

    @Override
    public Enumeration getGroupNames(String username)
            throws InvalidOperationException, NoSuchUserException
    {
        if (principal != null && principal.getName().equals(username))
        {
            return findUserGroupsBy(principal).elements();
        }
        return findUserGroupsBy(loadUserByLogin(username)).elements();
    }

    public Vector<String> findUserGroupsBy(LibraryPrincipal libraryPrincipal)
    {
        LOGGER.info("looking for permissions...");
        Vector<String> groups = new Vector<String>();

        switch (libraryPrincipal.getUserStatus())
        {
            case BLOCK:
            {
                LOGGER.info("The user is blocked");
                break;
            }
            case NORMAL:
            {
                Set<Permission> permissions = libraryPrincipal.getPermissions();
                for (Permission permission : permissions)
                {
                    LOGGER.debug("permission added: " + permission.name());
                    groups.add(permission.name());
                }
                break;
            }
            default:
                break;
        }
        return groups;
    }

    private LibraryPrincipal loadUserByLogin(String username) throws NoSuchUserException
    {
        LOGGER.info("loading user by username: " + username);

        LibraryPrincipal libraryPrincipal = loadBy("Login", username);
        if (libraryPrincipal == null)
        {
            throw new NoSuchUserException("No user found with username: " + username);
        }
        return libraryPrincipal;
    }

    private LibraryPrincipal loadBy(String param, String value)
    {
        String namedQuery = "findUserBy" + param;
        Query query = EntityManagerProvider.getEntityManager().createNamedQuery(namedQuery,
                LibraryPrincipal.class);
        query.setParameter(param.toLowerCase(), value);
        List<LibraryPrincipal> result = query.getResultList();
        if (result.size() == 0)
        {
            return null;
        }
        LibraryPrincipal libraryPrincipal = result.get(0);
        EntityManagerProvider.getEntityManager().refresh(libraryPrincipal);
        principal = libraryPrincipal;

        return libraryPrincipal;
    }

    private LibraryPrincipal loadUserByUuid(String uuid)
    {
        LOGGER.info("loading user by uuid: " + uuid);

        return loadBy("Uuid", uuid);
    }

    public LibraryPrincipal authenticateUser(String username, char[] passwd) throws NoSuchUserException
    {
        LOGGER.info("authentificating user: " + username);

        String uuid = HashProvider.hashValue(username + String.valueOf(passwd));
        LibraryPrincipal libraryPrincipal = loadUserByUuid(uuid);
        if (libraryPrincipal != null && uuid.equals(libraryPrincipal.getUuid()))
        {
            return libraryPrincipal;
        }
        else
        {
            libraryPrincipal = loadUserByLogin(username);
            if (!libraryPrincipal.getPassword().equals(HashProvider.hashValue(String.valueOf(passwd))))
            {
                throw new NoSuchUserException("Wrong password");
            }
        }

        return libraryPrincipal;
    }

}
