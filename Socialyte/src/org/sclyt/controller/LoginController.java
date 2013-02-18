package org.sclyt.controller;

import java.io.IOException;
import java.io.PrintWriter;

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
		PrintWriter out = response.getWriter();
		
		if (req.getParameter("logout") != null)
		{
			session.removeAttribute("session");
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Home");
			rd.forward(req, response);			
		}
		else
		{
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			//out.println(username + " :: " + password);
			
			Login login = new Login(username, password);
			
			//out.println(login.getUserPass());
			
			boolean success = false;
			
			if (login.setup())
				success = login.execute();
			
			if (success)
			{
				//out.println("LOGIN!");
				Session thisSession = login.createSession();
				
				session.setAttribute("session", thisSession);
				
				//out.println(session.getAttribute("username"));
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Home");
				rd.forward(req, response);
			}
			else
			{
				req.setAttribute("invalid_login", true);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Home");
				rd.forward(req, response);
			}
		}
	}

}
