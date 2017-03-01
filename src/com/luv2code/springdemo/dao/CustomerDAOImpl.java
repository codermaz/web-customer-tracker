package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject Session Factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
//	@Transactional   --- removed, while defined in Service Layer
	public List<Customer> getCustomers() {    // import java.util.List;

		// get the current hibernate session 
		Session currentSession= sessionFactory.getCurrentSession();  // import org.hibernate.Session;
		
		// create a query
		Query<Customer> theQuery = currentSession.createQuery("from Customer", Customer.class); // import org.hibernate.query.Query; (hibernate spaeter als 5.2)
		
		// execute query and get the result list
		List<Customer> theCustomers = theQuery.getResultList();
		
		// return the results
		return theCustomers;
	}

}
