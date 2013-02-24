package org.sclyt.store;

import me.prettyprint.hector.api.Keyspace;


public class CassandraStore {
	
	String cluster_name = "Test Cluster";
	String host = "77.99.214.115";
	String port = "9160";
	Keyspace cassKeyspace;
	
	public CassandraStore()
	{
	}

	static private CassandraStore _instance = null;

	static public CassandraStore instance()
	{
		if (null == _instance)
		{
			_instance = new CassandraStore();
		}
		return _instance;
	}
	
	public String getCluster()
	{
		return cluster_name;
	}
	
	public String getHost()
	{
		return host;
	}
	
	public String getPort()
	{
		return port;
	}
	
	public Keyspace getKeyspace()
	{
		return cassKeyspace;
	}
	
	public void setKeyspace(Keyspace _keyspace)
	{
		cassKeyspace = _keyspace;
	}
}
