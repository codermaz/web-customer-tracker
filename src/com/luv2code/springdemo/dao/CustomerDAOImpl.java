package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject Session Factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	// @Transactional --- removed, while defined in Service Layer
	public List<Customer> getCustomers() { // import java.util.List;

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession(); // import
																		// org.hibernate.Session;
		// create a query and sort by last name
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);
		// import org.hibernate.query.Query; (hibernate spaeter als 5.2)
		// execute query and get the result list
		List<Customer> theCustomers = theQuery.getResultList();
		// return the results
		return theCustomers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession(); // import
																		// org.hibernate.Session;
		// save the customer
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession(); // import
																		// org.hibernate.Session;
		// read or retrieve from database using the primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);

		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession(); // import
																		// org.hibernate.Session;
		// delete the customer from database using the primary key
		Query<Customer> theQuery = currentSession.createQuery("delete from Customer where id=:customerId");
		// import org.hibernate.query.Query; (hibernate spaeter als 5.2)
		theQuery.setParameter("customerId", theId);
		theQuery.executeUpdate();

		// Alternative Lösung - Contra : 2 * DB transaction
		// Customer customer = currentSession.get(Customer.class, theId);
		// currentSession.delete(customer);

	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession(); // import
																		// org.hibernate.Session;

		// Query<Customer> theQuery = currentSession.createQuery("from Customer
		// where firstName=:customerName");
		// // import org.hibernate.query.Query; (hibernate spaeter als 5.2)
		// theQuery.setParameter("customerName", theSearchName);

		Query theQuery = null;

		// only search by name if theSearchName is not empty
		if (theSearchName != null && theSearchName.trim().length() > 0) {
			// search for firstName or lastName ... case insensitive
			theQuery = currentSession.createQuery(
					"from Customer where lower(firstName) like :theName or lower(lastName) like :theName",
					Customer.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");

		} else {
			// theSearchName is empty ... so just get all customers
			theQuery = currentSession.createQuery("from Customer", Customer.class);
		}

		// execute query and get the result list
		List<Customer> theCustomers = theQuery.getResultList();

		return theCustomers;
	}

}
