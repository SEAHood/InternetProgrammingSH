package sclyt.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
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
	ColumnFamilyTemplate<UUID, String> UUIDtemplate;
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
		
			//DEFINE TEMPLATE FOR COLUMNFAMILY
			UUIDtemplate =  new ThriftColumnFamilyTemplate<UUID, String>(cassKeyspace,
	                                                               columnFamily,
	                                                               UUIDSerializer.get(),
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
		
		int row_count = 100;

        RangeSlicesQuery<UUID, String, String> rangeSlicesQuery = HFactory
            .createRangeSlicesQuery(cassKeyspace, UUIDSerializer.get(), StringSerializer.get(), StringSerializer.get())
            .setColumnFamily("POSTS")
            .setRange("", "", false, 100)
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
	
	public boolean createPost(String _full_name, String _body, String _tags)
	{
		UUID timeUUID = generateTimeUUID();
		
		// UPDATE / CREATE CODE
		// <String, String> correspond to key and Column name.
		ColumnFamilyUpdater<UUID, String> nameUpdater = UUIDtemplate.createUpdater(timeUUID);
		nameUpdater.setString("full_name", _full_name);
		nameUpdater.setLong("time", System.currentTimeMillis());
		
		ColumnFamilyUpdater<UUID, String> bodyUpdater = UUIDtemplate.createUpdater(timeUUID);
		bodyUpdater.setString("body", _body);
		bodyUpdater.setLong("time", System.currentTimeMillis());
		
		ColumnFamilyUpdater<UUID, String> tagsUpdater = UUIDtemplate.createUpdater(timeUUID);
		tagsUpdater.setString("tags", _tags);
		tagsUpdater.setLong("time", System.currentTimeMillis());

		UUIDtemplate.update(nameUpdater);
		UUIDtemplate.update(bodyUpdater);
		UUIDtemplate.update(tagsUpdater);
		try {
		    
		} catch (HectorException e) {
		    return false;
		}
		
		return true;
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
