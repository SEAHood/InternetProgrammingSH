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

import org.sclyt.model.Posts;
import org.sclyt.store.Session;

import me.prettyprint.hector.api.beans.Row;


/**
 * Servlet implementation class HomeController
 */
@WebServlet("/HomeController")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		directToHome(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		directToHome(request, response);
	}
	
	
	private void directToHome(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		HttpSession session = req.getSession();
		//String session_username = (String)session.getAttribute("username");
		Session thisSession = (Session)session.getAttribute("session");
		
		//posts.getPosts();

		//PrintWriter out = res.getWriter();
		//out.println("TEST");
		
		
		if(thisSession != null)
		{
			Posts posts = new Posts();
			req.setAttribute("Posts", posts.getPosts());
			req.setAttribute("Session", thisSession);
			
			
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/home.jsp");
			rd.forward(req, res);
		
		}
		else
		{
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			rd.forward(req, res);
		}
	}

}
