package biz.fz5.expensemanagement.model.hibernate;


import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import biz.fz5.expensemanagement.model.Constants;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html }.
 */
public class HibernateSessionFactory {

	private static Configuration configuration;

	private static SessionFactory sessionFactory;

	private static final ThreadLocal threadTransaction = new ThreadLocal();

	private static final ThreadLocal threadSession = new ThreadLocal();

	private static final ThreadLocal threadInterceptor = new ThreadLocal();

	protected static final Logger log = Logger
			.getLogger(HibernateSessionFactory.class.getName());

	private static String CONFIG_FILE_LOCATION = Constants
			.getValue("configLocation");

	// Create the initial SessionFactory from the default configuration files
	static {
		try {
			log.debug("CONFIG_FILE_LOCATION:" + CONFIG_FILE_LOCATION);
			System.out.println("CONFIG_FILE_LOCATION:" + CONFIG_FILE_LOCATION);
			configuration = new Configuration();
			configuration.configure(CONFIG_FILE_LOCATION);
			sessionFactory = configuration.buildSessionFactory();
			log.debug("sessionFactory:" + sessionFactory);
			System.out.println("sessionFactory:" + sessionFactory);

		} catch (Throwable ex) {
			ex.printStackTrace();
			log.error("Building SessionFactory failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Returns the SessionFactory used for this static class.
	 * 
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		/*
		 * Instead of a static variable, use JNDI: SessionFactory sessions =
		 * null; try { Context ctx = new InitialContext(); String jndiName =
		 * "java:hibernate/HibernateFactory"; sessions =
		 * (SessionFactory)ctx.lookup(jndiName); } catch (NamingException ex) {
		 * throw new InfrastructureException(ex); } return sessions;
		 */
		return sessionFactory;
	}

	/**
	 * Returns the original Hibernate configuration.
	 * 
	 * @return Configuration
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * Rebuild the SessionFactory with the static Configuration.
	 * 
	 */
	public static void rebuildSessionFactory() throws HibernateException {
		synchronized (sessionFactory) {
			try {
				sessionFactory = getConfiguration().buildSessionFactory();
			} catch (Exception ex) {
				throw new HibernateException(ex);
			}
		}
	}

	/**
	 * Rebuild the SessionFactory with the given Hibernate Configuration.
	 * 
	 * @param cfg
	 */
	public static void rebuildSessionFactory(Configuration cfg)
			throws HibernateException {
		synchronized (sessionFactory) {
			try {
				sessionFactory = cfg.buildSessionFactory();
				configuration = cfg;
			} catch (Exception ex) {
				throw new HibernateException(ex);
			}
		}
	}

	/**
	 * Retrieves the current Session local to the thread.
	 * <p/>
	 * If no Session is open, opens a new Session for the running thread.
	 * 
	 * @return Session
	 */
	public static Session getSession() throws HibernateException {
		Session s = (Session) threadSession.get();
		if (s == null) {
			log.debug("Opening new Session for this thread.");
			if (getInterceptor() != null) {
				log.debug("Using interceptor: " + getInterceptor().getClass());
				s = getSessionFactory().openSession(getInterceptor());
			} else {
				s = getSessionFactory().openSession();
			}
			threadSession.set(s);
		}
		// log.debug("getSession - s[" + s.hashCode() + "]:"+s);
		return s;
	}

	/**
	 * Closes the Session local to the thread.
	 */
	public static void closeSession() {
		try {
			Session s = (Session) threadSession.get();
			// log.debug("closeSession - s["+s.hashCode()+"]");
			threadSession.set(null);
			if (s != null && s.isOpen()) {
				// log.debug("Closing Session of this thread.");
				s.close();
			}
		} catch (Exception e) {
			log.error("closeSession e:" + e);
			e.printStackTrace();
		}

	}

	/**
	 * Start a new database transaction.
	 */
	public static void beginTransaction() throws HibernateException {
		// Transaction tx = (Transaction) threadTransaction.get();
		// Transaction tx = ((Session)threadSession.get()).beginTransaction();
		Transaction tx = getSession().beginTransaction();
		// log.debug("beginTransaction - tx["+ tx.hashCode() + "]:"+tx);
		// if (tx == null) {
		// log.debug("Starting new database transaction in this thread...");
		// tx = getSession().beginTransaction();
		// log.debug("beginTransaction - tx["+ tx.hashCode() + "]:"+tx);
		threadTransaction.set(tx);
		// }
	}

	/**
	 * Commit the database transaction.
	 */
	public static void commitTransaction() {

		log.debug("commitTransaction");

		try {
			Transaction tx = (Transaction) threadTransaction.get();
			// log.debug("commitTransaction - tx["+ tx.hashCode() + "]:"+tx);
			// log.debug("commitTransaction - tx.wasCommitted():"+tx.wasCommitted());
			// log.debug("commitTransaction - tx.wasRolledBack():"+tx.wasRolledBack());
			if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
				log.debug("Committing database transaction of this thread.");
				tx.commit();
			}
			threadTransaction.set(null);
		} catch (Exception e) {
			log.error("commitTransaction e:" + e);
			e.printStackTrace();
		}
	}

	/**
	 * Commit the database transaction.
	 */
	public static void rollbackTransaction() throws HibernateException {

		Transaction tx = (Transaction) threadTransaction.get();

		try {
			threadTransaction.set(null);
			if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
				log
						.debug("Tyring to rollback database transaction of this thread.");
				tx.rollback();
			}
		} finally {
			closeSession();
		}
	}

	/**
	 * Reconnects a Hibernate Session to the current Thread.
	 * 
	 * @param session
	 *            The Hibernate Session to be reconnected.
	 */
	public static void reconnect(Session session) throws HibernateException {
		session.reconnect();
		threadSession.set(session);
	}

	/**
	 * Disconnect and return Session from current Thread.
	 * 
	 * @return Session the disconnected Session
	 */
	public static Session disconnectSession() throws HibernateException {

		Session session = getSession();
		threadSession.set(null);
		if (session.isConnected() && session.isOpen())
			session.disconnect();
		return session;
	}

	/**
	 * Register a Hibernate interceptor with the current thread.
	 * <p>
	 * Every Session opened is opened with this interceptor after registration.
	 * Has no effect if the current Session of the thread is already open,
	 * effective on next close()/getSession().
	 */
	public static void registerInterceptor(Interceptor interceptor) {
		threadInterceptor.set(interceptor);
	}

	private static Interceptor getInterceptor() {
		Interceptor interceptor = (Interceptor) threadInterceptor.get();
		return interceptor;
	}

}