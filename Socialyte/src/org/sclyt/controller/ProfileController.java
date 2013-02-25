package org.sclyt.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sclyt.model.ProfileConnector;
import org.sclyt.store.ProfileStore;
import org.sclyt.store.Session;

/**
 * Servlet implementation class ProfileController
 */
@WebServlet("/ProfileController")
public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		if ((Session)session.getAttribute("session") != null)
		{
			Session thisSession = (Session)session.getAttribute("session");
			request.setAttribute("session", thisSession);
			ProfileStore profile = null;
			String username = request.getPathInfo();
			username = username.replace("/", "");
			System.out.println(username);
		
			ProfileConnector connector = new ProfileConnector(username);
			
			if (connector.setup())
			{
				//Direct to profile page
				profile = connector.execute();
				profile.normaliseNulls();
				request.setAttribute("profile", profile);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/profile.jsp");
				rd.forward(request, response);
			}
		}
		else
		{
			//No user logged in - direct to login
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Session thisSession = (Session)session.getAttribute("session");
		request.setAttribute("session", thisSession);
	}

}
