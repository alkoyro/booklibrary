package by.es.realm.login;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;

import by.es.realm.LibraryRealm;
import by.es.realm.entity.LibraryPrincipal;
import by.es.realm.entity.predefined.Permission;

import com.sun.appserv.security.AppservPasswordLoginModule;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;

public class LibraryLoginModule extends AppservPasswordLoginModule
{	
	private static final Logger LOGGER = Logger.getLogger(LibraryLoginModule.class); 

	@Override
	protected void authenticateUser() throws LoginException
	{
		if (!(_currentRealm instanceof LibraryRealm))
		{
			throw new LoginException("customrealm:badrealm");
		}
		LibraryRealm realm = (LibraryRealm) _currentRealm;

		LibraryPrincipal userPrincipal;
		try
		{
			userPrincipal = realm.authenticateUser(_username,
					_passwd);
		}
		catch (NoSuchUserException e)
		{
			LOGGER.error(e.getMessage(), e);
			throw new LoginException(e.getMessage());
		}
		Set<Principal> principals = _subject.getPrincipals();
		if (!principals.contains(userPrincipal))
		{
			principals.add(userPrincipal);
		}

		Vector<String> groups = realm.findUserGroupsBy(userPrincipal);

		commitUserAuthentication(groups.toArray(new String[groups.size()]));
	}

}
