package org.sclyt.model;

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
	ColumnFamilyTemplate<UUID, String> UUIDTemplate;
	ColumnFamilyTemplate<Long, String> LongTemplate;
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
			UUIDTemplate =  new ThriftColumnFamilyTemplate<UUID, String>(cassKeyspace,
	                                                               columnFamily,
	                                                               UUIDSerializer.get(),
	                                                               StringSerializer.get());	
			
			//DEFINE TEMPLATE FOR COLUMNFAMILY
			LongTemplate =  new ThriftColumnFamilyTemplate<Long, String>(cassKeyspace,
	                                                               columnFamily,
	                                                               LongSerializer.get(),
	                                                               StringSerializer.get());	
		
			return true;
		}
		catch (HectorException e)
		{
			return false;
		}
	}
	
	public LinkedList<Row<Long, String, String>> queryPosts()
	{		
		LinkedList<Row<Long, String, String>> llist = new LinkedList<Row<Long, String, String>>();
		
		int row_count = 100;

        RangeSlicesQuery<Long, String, String> rangeSlicesQuery = HFactory
            .createRangeSlicesQuery(cassKeyspace, LongSerializer.get(), StringSerializer.get(), StringSerializer.get())
            .setColumnFamily("ALL_POSTS")
            .setRange("", "", false, 100)
            .setRowCount(row_count);

        rangeSlicesQuery.setKeys(null, null);
        QueryResult<OrderedRows<Long, String, String>> result = rangeSlicesQuery.execute();
        OrderedRows<Long, String, String> rows = result.get();
        Iterator<Row<Long, String, String>> rowsIterator = rows.iterator();
 

        while (rowsIterator.hasNext()) 
        {
        	Row<Long, String, String> row = rowsIterator.next();

            if (row.getColumnSlice().getColumns().isEmpty()) 
                continue;

            llist.add(row);
        }

	    
	    return llist;
	}
	
	public boolean createPost(String _full_name, String _body, String _tags)
	{
		UUID timeUUID = generateTimeUUID();
		long timestamp = System.currentTimeMillis();
		// UPDATE / CREATE CODE
		// <String, String> correspond to key and Column name.
		//ColumnFamilyUpdater<UUID, String> nameUpdater = UUIDTemplate.createUpdater(timeUUID);
		ColumnFamilyUpdater<Long, String> nameUpdater = LongTemplate.createUpdater(timestamp);
		nameUpdater.setString("full_name", _full_name);
		nameUpdater.setLong("time", System.currentTimeMillis());
		
		//ColumnFamilyUpdater<UUID, String> bodyUpdater = UUIDTemplate.createUpdater(timeUUID);
		ColumnFamilyUpdater<Long, String> bodyUpdater = LongTemplate.createUpdater(timestamp);
		bodyUpdater.setString("body", _body);
		bodyUpdater.setLong("time", System.currentTimeMillis());
		
		//ColumnFamilyUpdater<UUID, String> tagsUpdater = UUIDTemplate.createUpdater(timeUUID);
		ColumnFamilyUpdater<Long, String> tagsUpdater = LongTemplate.createUpdater(timestamp);
		tagsUpdater.setString("tags", _tags);
		tagsUpdater.setLong("time", System.currentTimeMillis());

		
		try {
			LongTemplate.update(nameUpdater);
			LongTemplate.update(bodyUpdater);
			LongTemplate.update(tagsUpdater);		    
		} catch (HectorException e) {
			System.out.println(e.getMessage());
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
