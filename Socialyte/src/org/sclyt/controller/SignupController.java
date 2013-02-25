package org.sclyt.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sclyt.model.DataValidator;
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
		
		//Create signup object and validate
		Signup newAccount = new Signup(first_name, surname, new_username, new_password, new_password_c, email, default_avatar);
		DataValidator validator = new DataValidator(newAccount);
		String validation_result = validator.validate();
		
		System.out.println(validation_result);
		if (validation_result != "")
		{
			//Validation error
			request.setAttribute("validation_error", validation_result);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
		else
		{
			if (newAccount.execute())
			{
				//Account created
				Date date = new Date();
				System.out.println("[" + date + "] New account created:" + first_name + "," + surname + "," + new_username + "," + new_password + "," + email);
				request.setAttribute("account_created", true);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
				rd.forward(request, response);
			}
			else
			{
				//Unknown problem
				System.out.println("newAccount.execute() failed.");
				request.setAttribute("account_creation_issue", true);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
				rd.forward(request, response);
			}
		}
	}

}
