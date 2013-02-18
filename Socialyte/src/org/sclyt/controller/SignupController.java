package org.sclyt.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sclyt.model.Signup;

/**
 * Servlet implementation class SignupController
 */
@WebServlet("/SignupController")
public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupController() {
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
		String first_name = request.getParameter("first_name");
		String surname = request.getParameter("surname");
		String new_username = request.getParameter("new_username");
		String new_password = request.getParameter("new_password");
		String new_password_c = request.getParameter("new_password_c");
		String email = request.getParameter("email");
		
		String default_avatar = "/Socialyte/img/profiles/default.png";
		
		Signup newAccount = new Signup(first_name, surname, new_username, new_password, email, default_avatar);
		
		if (newAccount.execute())
		{
			System.out.println("newAccount.execute() succeeded - new account created.");
			request.setAttribute("account_created", true);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Home");
			rd.forward(request, response);
		}
		else
		{
			System.out.println("newAccount.execute() failed.");
		}
		
		//DO VALIDATION
		
		
		//DO DATABASE INPUT
		
		
		//FORWARD TO LOGIN WITH DETAILS ON REQUEST
	}

}
