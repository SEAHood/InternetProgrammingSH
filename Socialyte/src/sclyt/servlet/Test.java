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
		
		
		/*
		
		AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
	    .forCluster("Test Cluster")
	    .forKeyspace("TESTSPACE")
	    .withAstyanaxConfiguration(new AstyanaxConfigurationImpl()      
	        .setDiscoveryType(NodeDiscoveryType.NONE)
	    )
	    .withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl("MyConnectionPool")
	        .setPort(9160)
	        .setMaxConnsPerHost(1)
	        .setSeeds("127.0.0.1:9160")
	    )
	    .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
	    .buildKeyspace(ThriftFamilyFactory.getInstance());
		
		try {
		context.start();
		}
		catch (Exception e)
		{
			out.println("<p>" + e.getMessage() + "</p>");
		
		}
		
		Keyspace keyspace = context.getEntity();
		
		ColumnFamily<String, String> CF_USER_INFO =
				  new ColumnFamily<String, String>(
				    "users",              // Column Family Name
				    StringSerializer.get(),   // Key Serializer
				    StringSerializer.get());  // Column Serializer
		
		OperationResult<ColumnList<String>> result = null;
		try {
			result = keyspace.prepareQuery(CF_USER_INFO)
			    .getKey("Key1")
			    .execute();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				ColumnList<String> columns = result.getResult();

				// Lookup columns in response by name 
				String address = columns.getColumnByName("full_name").getStringValue();

				// Or, iterate through the columns
				for (Column<String> c : result.getResult()) {
				  out.println("<p>" + c.getName() + " :: " + address + "</p>");
				}
		
		*/
		
		
		
		Cluster myCluster = null;
		
		try{
			myCluster = HFactory.getOrCreateCluster("Test Cluster","192.168.0.100:9160");
		}
		catch (Exception e)
		{

		}
		
		
		Keyspace ksp = null;
		
		try{
		ksp = HFactory.createKeyspace("TESTSPACE", myCluster);
		}
		catch (Exception ex)
		{

		    out.println("<p>" + ex.getMessage() + "</p>");
			
		}
		
		
		
		ColumnFamilyTemplate<String,String> template =  new ThriftColumnFamilyTemplate<String, String>(ksp,
                                                               "users",
                                                               StringSerializer.get(),
                                                               StringSerializer.get());
		
		
		try {
		    ColumnFamilyResult<String, String> res = template.queryColumns("shood");
		    String value = res.getString("full_name");
		    out.println("<p>The full name of user 'shood' is: " + value + "</p>");
		    
		    // value should be "www.datastax.com" as per our previous insertion.
		} catch (HectorException e) {
		    // do something ...
		    out.println("<p>" + e.getMessage() + "</p>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	
	
}
