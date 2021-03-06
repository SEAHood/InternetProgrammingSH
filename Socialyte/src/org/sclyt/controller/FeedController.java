package org.sclyt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sclyt.model.Login;
import org.sclyt.model.Posts;
import org.sclyt.store.Session;

import me.prettyprint.hector.api.beans.Row;


/**
 * Servlet implementation class FeedController
 */
@WebServlet("/FeedController")
public class FeedController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		directToFeed(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		directToFeed(request, response);
	}
	
	
	//Directs to feed
	private void directToFeed(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		HttpSession session = req.getSession();
		Session thisSession = (Session)session.getAttribute("session");
				
		if(thisSession != null)
		{
			//User logged in - direct to feed.jsp
			Posts posts = new Posts();
			req.setAttribute("posts", posts.getSubscriptionPosts(thisSession.getUsername()));
			req.setAttribute("session", thisSession);
			req.setAttribute("feed", "sub_posts");
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/feed.jsp");
			rd.forward(req, res);
			System.out.println("LOGGED IN");
		}
		else
		{
			if (req.getMethod().equals("POST") && req.getAttribute("validation_error") == null)
			{
				//Login attempt - direct to login controller
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Login");
				rd.forward(req, res);
			}
			else
			{
				//Validaton problem - direct to login.jsp
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
				rd.forward(req, res);
			}
		}
	}
}
