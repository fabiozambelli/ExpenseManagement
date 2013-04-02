package biz.fz5.expensemanagement.filter;

import biz.fz5.expensemanagement.model.hibernate.HibernateSessionFactory;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * Pattern che si occupa di gestire la transazione hibernate. La transazione ha
 * lo scope request.
 * 
 * @author fabiozambelli
 * 
 */
public class HibernateFilter implements Filter {

	protected static final Logger log = Logger.getLogger(HibernateFilter.class
			.getName());

	/**
	 * Load a query map using the query map conf file defined in web.xml
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

		log
				.debug("Servlet filter init, now opening/closing a Session for each request.");

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession httpSession = httpServletRequest.getSession();
		log.debug("doFilter - httpSession:" + httpSession.hashCode());
		Session session = HibernateSessionFactory.getSession();
		log.debug("doFilter - HibernateSession after getSession");
		log.debug("doFilter - HibernateSession[" + session.hashCode() + "]:"
				+ session + "  , after getSession");

		try {
			HibernateSessionFactory.beginTransaction();
			log.debug("doFilter - HibernateSession after beginTransaction");
			log.debug("doFilter - HibernateSession[" + session.hashCode()
					+ "]:" + session + "  , after beginTransaction");

			chain.doFilter(request, response);

			// Commit any pending database transaction.
			HibernateSessionFactory.commitTransaction();
			log.debug("doFilter - HibernateSession after commitTransaction");
		} finally {

			// No matter what happens, close the Session.
			HibernateSessionFactory.closeSession();
			log.debug("doFilter - HibernateSession after closeSession");
		}
	}

	public void destroy() {
	}

}
