package biz.fz5.expensemanagement.model.hibernate;

// default package

import org.hibernate.Session;

/**
 * Data access object (DAO) for domain model
 * 
 * @author MyEclipse Persistence Tools
 */
public class BaseHibernateDAO implements IBaseHibernateDAO {

	public Session getSession() {
		return HibernateSessionFactory.getSession();
	}

	public void closeSession() {

		HibernateSessionFactory.closeSession();
	}

	public void beginTransaction() {

		HibernateSessionFactory.beginTransaction();
	}

	public void commitTransaction() {

		HibernateSessionFactory.commitTransaction();

	}

}