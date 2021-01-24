package demo.controller;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import model.Student;

@Controller
@RequestMapping("/student")
public class StudentController {

	@RequestMapping(method = RequestMethod.GET)
	public String listStudent(ModelMap model) {
		List<Student> data = getAll();
		System.out.println(data);
		model.addAttribute("message", data);
		return "showStudent";
	}

	List<Student> getAll() {
		/*
		Configuration configObj = new Configuration();
		configObj.addClass(model.Student.class);
		configObj.configure("hibernate.cfg.xml");

		// Since Hibernate Version 4.x, ServiceRegistry Is Being Used
		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder()
				.applySettings(configObj.getProperties()).build();
	    Session session = null;
		*/
		SessionFactory sessionFactory=null;
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
    	        .configure() // configures settings from hibernate.cfg.xml
    	        .build();
    	try {
    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	} catch (Exception ex) {
    	    StandardServiceRegistryBuilder.destroy(registry);
    	    System.out.println("Setup Failed:"+ex.getMessage());
    	}
    	Session session=sessionFactory.openSession();
		//session = configObj.buildSessionFactory(serviceRegistryObj).openSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			List data = session.createQuery("FROM Student").list();
			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Student st = (Student) iterator.next();
				System.out.print("Student ID: " + st.getStudentId());
				System.out.print("  Last Name: " + st.getName());
				System.out.println("  Address: " + st.getAddress());
			}
			tx.commit();
			return data;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
	public void addStu(Student stu) {
		/*
		Configuration configObj = new Configuration();
		configObj.addClass(model.Student.class);
		configObj.configure("hibernate.cfg.xml");

		// Since Hibernate Version 4.x, ServiceRegistry Is Being Used
		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder()
				.applySettings(configObj.getProperties()).build();
				*/
		SessionFactory sessionFactory=null;
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
    	        .configure() // configures settings from hibernate.cfg.xml
    	        .build();
    	try {
    	    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    	} catch (Exception ex) {
    	    StandardServiceRegistryBuilder.destroy(registry);
    	    System.out.println("Setup Failed:"+ex.getMessage());
    	}
    	Session sessionObj=sessionFactory.openSession();
		//Session sessionObj = null;
		// Creating Hibernate SessionFactory Instance
		try {
			//sessionObj = configObj.buildSessionFactory(serviceRegistryObj).openSession();
			sessionObj.beginTransaction();
			sessionObj.save(stu);

			System.out.println("\n.......Records Saved Successfully To The Database.......\n");

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();

		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
	}

	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
	public ModelAndView addStudent(@ModelAttribute("student") Student stu) {
		System.out.println("stu obj:" + stu);
		addStu(stu);		
		return  new ModelAndView("showStudent","message",getAll());

	}


}