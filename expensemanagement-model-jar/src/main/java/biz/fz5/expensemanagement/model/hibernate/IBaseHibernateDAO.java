package biz.fz5.expensemanagement.model.hibernate;

import org.hibernate.Session;

/**
 * Data access interface for domain model
 * 
 * @author MyEclipse Persistence Tools
 */
public interface IBaseHibernateDAO {

	public Session getSession();

	public void closeSession();

	public void beginTransaction();

	public void commitTransaction();
}