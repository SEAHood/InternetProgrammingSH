package org.sclyt.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sclyt.model.PostCreator;
import org.sclyt.store.Session;


/**
 * Servlet implementation class NewPostController
 */
@WebServlet("/NewPostController")
public class NewPostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewPostController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/newpost.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Session thisSession = (Session)session.getAttribute("session");
		String full_name = thisSession.getUsername();
		String body = request.getParameter("body");
		String tags = request.getParameter("tags");
		
		PostCreator creator = new PostCreator(full_name, body, tags);
		if (creator.create())
		{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/post_success.jsp");
			rd.forward(request, response);
		}
		else
		{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/post_fail.jsp");
			rd.forward(request, response);
		}
		
	}

}
