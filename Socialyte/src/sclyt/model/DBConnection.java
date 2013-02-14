package sclyt.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;

public class DBConnection {
	
	ColumnFamilyTemplate<String, String> template;
	Cluster cassCluster;
	Keyspace cassKeyspace;
	String _CF;
	
	public DBConnection()
	{
		
	}
	
	public boolean connect(String columnFamily)
	{		
		try {
			//INITIALISE CONNECTION
			
			
			
			cassCluster = HFactory.getOrCreateCluster("Test Cluster","77.99.214.115:9160");
			
			cassKeyspace = HFactory.createKeyspace("TESTSPACE", cassCluster);
					
			
			//DEFINE TEMPLATE FOR COLUMNFAMILY
			template =  new ThriftColumnFamilyTemplate<String, String>(cassKeyspace,
	                                                               columnFamily,
	                                                               StringSerializer.get(),
	                                                               StringSerializer.get());		
			return true;
		}
		catch (HectorException e)
		{
			return false;
		}
	}
	
	public LinkedList<Row<UUID, String, String>> queryPosts()
	{		
		LinkedList<Row<UUID, String, String>> llist = new LinkedList<Row<UUID, String, String>>();
		
		int row_count = 3;

        RangeSlicesQuery<UUID, String, String> rangeSlicesQuery = HFactory
            .createRangeSlicesQuery(cassKeyspace, UUIDSerializer.get(), StringSerializer.get(), StringSerializer.get())
            .setColumnFamily("POSTS")
            .setRange("", "", false, 3)
            .setRowCount(row_count);

        rangeSlicesQuery.setKeys(null, null);
            
        QueryResult<OrderedRows<UUID, String, String>> result = rangeSlicesQuery.execute();
        OrderedRows<UUID, String, String> rows = result.get();
        Iterator<Row<UUID, String, String>> rowsIterator = rows.iterator();
 

        while (rowsIterator.hasNext()) 
        {
        	Row<UUID, String, String> row = rowsIterator.next();

            if (row.getColumnSlice().getColumns().isEmpty()) 
                continue;

            llist.add(row);
        }

	    
	    return llist;
	}
	
	private UUID generateTimeUUID()
	{
		UUID timeUUID = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
		
		return timeUUID;
	}
		
	//CONNECTION SETUP
	
	
	/*
	// READ CODE
    ColumnFamilyResult<String, String> res = template.queryColumns("shood");
    String value = res.getString("full_name");
    //out.println("<p>The full name of user 'shood' is: " + value + "</p>"); //DISPLAY
	    
	
	// UPDATE / CREATE CODE
	// <String, String> correspond to key and Column name.
	ColumnFamilyUpdater<String, String> updater = template.createUpdater("jbloggs");
	updater.setString("full_name", "Joe Bloggs");
	updater.setLong("time", System.currentTimeMillis());

	try {
	    template.update(updater);
	} catch (HectorException e) {
	    // do something ...
	}*/

}
