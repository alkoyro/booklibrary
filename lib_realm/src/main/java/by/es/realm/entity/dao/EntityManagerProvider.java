package by.es.realm.entity.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider
{
	private static EntityManager entityManager;
	
	private EntityManagerProvider()
	{
	}
	
	public static EntityManager getEntityManager()
	{
		if(entityManager == null)
		{
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("libraryRealm");
			entityManager = entityManagerFactory.createEntityManager();
		}
		return entityManager;
	}
	
}
