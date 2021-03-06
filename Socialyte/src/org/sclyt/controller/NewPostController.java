package org.sclyt.controller;

import java.io.IOException;
import java.util.Date;

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
		HttpSession session = request.getSession();
		if ((Session)session.getAttribute("session") != null)
		{
			Session thisSession = (Session)session.getAttribute("session");
			request.setAttribute("session", thisSession);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/newpost.jsp");
			rd.forward(request, response);
		}
		else
		{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
	}

	
	//Creates a new post
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if ((Session)session.getAttribute("session") != null)
		{
			Session thisSession = (Session)session.getAttribute("session");
			String username = thisSession.getUsername();
			String full_name = thisSession.getFullName();
			String body = request.getParameter("body");
			String tags = request.getParameter("tags");
			request.setAttribute("session", thisSession);
			
			PostCreator creator = new PostCreator(username, full_name, body, tags);
			if (creator.create())
			{
				//Post created
				Date date = new Date();
				System.out.println("[" + date + "] " + full_name + " created post body {" + body + "}, tags{" + tags + "}" );
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/feedbackpages/post_success.jsp");
				rd.forward(request, response);
			}
			else
			{
				//Post failed
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/feedbackpages/post_fail.jsp");
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
}
