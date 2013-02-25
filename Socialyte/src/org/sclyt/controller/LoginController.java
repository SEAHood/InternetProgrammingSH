package org.sclyt.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sclyt.model.Login;
import org.sclyt.store.Session;


/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		attemptLogin(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		attemptLogin(request, response);
	}
	
	
	private void attemptLogin(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = req.getSession();
		
		if (req.getParameter("logout") != null)
		{
			//Log the user out
			session.removeAttribute("session");
			req.removeAttribute("session");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			rd.forward(req, response);			
		}
		else
		{
			//Log the user in
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			Login login = null;
			boolean success = false;
						
			if (username != "" && password != "")
			{
				login = new Login(username, password);
							
				if (login.setup())
					success = login.execute();
			}
			
			if (success)
			{
				Session thisSession = login.createSession();
				
				session.setAttribute("session", thisSession);
				
				//Logged in - direct to feed
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Feed");
				rd.forward(req, response);
			}
			else
			{
				req.setAttribute("invalid_login", true);
				//Login failed - return to login.jsp
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
				rd.forward(req, response);
			}
		}
	}
}
