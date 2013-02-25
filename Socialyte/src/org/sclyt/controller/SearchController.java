package org.sclyt.controller;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sclyt.model.Search;
import org.sclyt.store.ProfileStore;
import org.sclyt.store.Session;

/**
 * Servlet implementation class SearchController
 */
@WebServlet("/SearchController")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		directToSearch(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		directToSearch(request, response);
	}
	
	
	private void directToSearch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//Directs the browser to the search page
		HttpSession session = req.getSession();
		Session thisSession = (Session)session.getAttribute("session");
		
		if (thisSession != null)
		{
			if (req.getMethod().equals("POST"))
			{
				String first_name = req.getParameter("first_name");
				String surname = req.getParameter("surname");
				String email = req.getParameter("email");
				String city = req.getParameter("city");
				Search search = new Search(first_name, surname, email, city);
				
				LinkedList<ProfileStore> results = search.go();
				
				req.setAttribute("session", thisSession);
				req.setAttribute("search_results", results);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/profile_search.jsp");
				rd.forward(req, res);
			}
			else
			{
				req.setAttribute("session", thisSession);
				System.out.println(thisSession.toString());
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/profile_search.jsp");
				rd.forward(req, res);
			}
		}
		else
		{
			//No user logged in - direct to login
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			rd.forward(req, res);
		}
	}

}
