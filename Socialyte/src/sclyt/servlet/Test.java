package sclyt.servlet;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;


/**
 * Servlet implementation class Test
 */
@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
				
		
		//CONNECTION SETUP
		Cluster myCluster = HFactory.getOrCreateCluster("Test Cluster","192.168.0.100:9160");
		
		Keyspace ksp = HFactory.createKeyspace("TESTSPACE", myCluster);
				
		
		//DEFINE TEMPLATE FOR COLUMNFAMILY
		ColumnFamilyTemplate<String,String> template =  new ThriftColumnFamilyTemplate<String, String>(ksp,
                                                               "users",
                                                               StringSerializer.get(),
                                                               StringSerializer.get());
		
		
		// READ CODE
	    ColumnFamilyResult<String, String> res = template.queryColumns("shood");
	    String value = res.getString("full_name");
	    out.println("<p>The full name of user 'shood' is: " + value + "</p>"); //DISPLAY
		    
		
		// UPDATE / CREATE CODE
		// <String, String> correspond to key and Column name.
		ColumnFamilyUpdater<String, String> updater = template.createUpdater("jbloggs");
		updater.setString("full_name", "Joe Bloggs");
		updater.setLong("time", System.currentTimeMillis());

		try {
		    template.update(updater);
		} catch (HectorException e) {
		    // do something ...
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	
	
}
