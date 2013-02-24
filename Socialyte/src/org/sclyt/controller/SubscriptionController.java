package org.sclyt.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sclyt.model.Posts;
import org.sclyt.model.Subscribers;
import org.sclyt.model.Subscriptions;
import org.sclyt.store.Session;

/**
 * Servlet implementation class SubscriptionController
 */
@WebServlet("/SubscriptionController")
public class SubscriptionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubscriptionController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	HttpSession session = request.getSession();
    	Session thisSession = (Session)session.getAttribute("session");
    	String subscription_username = request.getRequestURI().replace("/Socialyte/Subscriptions/", "");
    	String username = thisSession.getUsername();
    	Subscriptions subscriptions = new Subscriptions(username);
    	subscriptions.deleteSubscription(subscription_username);
    	request.setAttribute("session", thisSession);
    	request.setAttribute("success", "true");
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		loadSubscriptions(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		addSubscription(request, response);
		//loadSubscriptions(request, response);
	}
	
	private void addSubscription(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Session thisSession = (Session)session.getAttribute("session");
		
		if(thisSession != null)
		{
			
			String username = thisSession.getUsername();
			String sub_username = req.getParameter("add_sub");
			Subscriptions subs = new Subscriptions(username);
			req.setAttribute("session", thisSession);
			
			if (subs.addSubscription(sub_username))
			{
				req.setAttribute("success", "true");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/feedbackpages/subscription_added.jsp");
				rd.forward(req, res);
			}
			else
			{
				req.setAttribute("success", "false");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/feedbackpages/subscription_added.jsp");
				rd.forward(req, res);
			}
		}
	}
	
	private void loadSubscriptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		HttpSession session = req.getSession();
		Session thisSession = (Session)session.getAttribute("session");
				
		if(thisSession != null)
		{
			String username = thisSession.getUsername();
			Subscriptions subscriptions = new Subscriptions(username);
			
			
			req.setAttribute("subscriptions", subscriptions.getSubscriptions());
			req.setAttribute("session", thisSession);
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/subscriptions.jsp");
			rd.forward(req, res);
			
		}
		else
		{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			rd.forward(req, res);
		}
	}

}
