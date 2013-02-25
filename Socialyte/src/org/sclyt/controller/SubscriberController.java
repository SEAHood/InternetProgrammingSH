package org.sclyt.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sclyt.model.Subscribers;
import org.sclyt.store.Session;

/**
 * Servlet implementation class SubscriberController
 */
@WebServlet("/SubscriberController")
public class SubscriberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubscriberController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	//Delete subscriber
    	HttpSession session = request.getSession();
    	Session thisSession = (Session)session.getAttribute("session");
    	String subscriber_username = request.getRequestURI().replace("/Socialyte/Subscribers/", "");
    	String username = thisSession.getUsername();
    	Subscribers subscribers = new Subscribers(username);
    	subscribers.deleteSubscriber(subscriber_username);
    	
    	request.setAttribute("session", thisSession);
    	request.setAttribute("success", "true");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		loadSubscribers(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		loadSubscribers(request, response);
	}
	
	
	//Load a list of subscribers and hand to JSP
	private void loadSubscribers(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		HttpSession session = req.getSession();
		Session thisSession = (Session)session.getAttribute("session");
				
		if(thisSession != null)
		{
			String username = thisSession.getUsername();
			Subscribers subscribers = new Subscribers(username);
			req.setAttribute("subscribers", subscribers.getSubscribers());
			req.setAttribute("session", thisSession);
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/subscribers.jsp");
			rd.forward(req, res);
		}
		else
		{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			rd.forward(req, res);
		}
	}

}
