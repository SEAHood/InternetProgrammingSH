package sclyt.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sclyt.model.Login;

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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		if (request.getParameter("logout") != null)
		{
			HttpSession session = request.getSession();
			session.removeAttribute("username");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Home");
			rd.forward(request, response);			
		}
		else
		{
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			//out.println(username + " :: " + password);
			
			Login login = new Login(username, password);
			
			//out.println(login.getUserPass());
			
			boolean success = false;
			
			if (login.setup())
				success = login.execute();
			
			if (success)
			{
				//out.println("LOGIN!");
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				//out.println(session.getAttribute("username"));
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Home");
				rd.forward(request, response);
			}
			else
				out.println("NO LOGIN!");
		}
		
		
	}

}
