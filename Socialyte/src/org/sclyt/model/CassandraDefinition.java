package org.sclyt.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import me.prettyprint.cassandra.model.BasicColumnDefinition;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ddl.ColumnDefinition;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;

public class CassandraDefinition {
	private String Keyspace;
	private int replicationFactor = 1;
	
	public CassandraDefinition(String keyspace){
		
		Keyspace = keyspace;
	}
	
	public void setup()
	{
		List<ColumnDefinition> userMetadata = createUserColumnMetadata();
		List<ColumnDefinition> loginMetadata = createLoginColumnMetadata();
		List<ColumnDefinition> postsMetadata = createPostsColumnMetadata();
				
		Cluster myCluster = HFactory.getOrCreateCluster("Test Cluster","134.36.36.83:9160");
		
		ColumnFamilyDefinition cfDefUser = HFactory.createColumnFamilyDefinition(Keyspace,
	            "USER",
	            ComparatorType.UTF8TYPE,
	            userMetadata);
		cfDefUser.setKeyValidationClass(ComparatorType.UTF8TYPE.getClassName());
		cfDefUser.setDefaultValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		ColumnFamilyDefinition cfDefLogin = HFactory.createColumnFamilyDefinition(Keyspace,
	            "LOGIN",
	            ComparatorType.UTF8TYPE,
	            loginMetadata);
		cfDefLogin.setKeyValidationClass(ComparatorType.UTF8TYPE.getClassName());
		cfDefLogin.setDefaultValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		ColumnFamilyDefinition cfDefAllPosts = HFactory.createColumnFamilyDefinition(Keyspace,
	            "ALL_POSTS",
	            ComparatorType.UTF8TYPE,
	            postsMetadata);
		cfDefAllPosts.setKeyValidationClass(ComparatorType.LONGTYPE.getClassName());
		cfDefAllPosts.setDefaultValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		
		ColumnFamilyDefinition cfDefSubscribedToBy = HFactory.createColumnFamilyDefinition(Keyspace,
	            "SUBSCRIBED_TO_BY",
	            ComparatorType.UTF8TYPE);
		cfDefSubscribedToBy.setKeyValidationClass(ComparatorType.UTF8TYPE.getClassName());
		cfDefSubscribedToBy.setDefaultValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		ColumnFamilyDefinition cfDefSubscribesTo = HFactory.createColumnFamilyDefinition(Keyspace,
	            "SUBSCRIBES_TO",
	            ComparatorType.UTF8TYPE);
		cfDefSubscribesTo.setKeyValidationClass(ComparatorType.UTF8TYPE.getClassName());
		cfDefSubscribesTo.setDefaultValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		ColumnFamilyDefinition cfDefUserPosts = HFactory.createColumnFamilyDefinition(Keyspace,
	            "USER_POST",
	            ComparatorType.LONGTYPE);
		cfDefUserPosts.setKeyValidationClass(ComparatorType.UTF8TYPE.getClassName());
		cfDefUserPosts.setDefaultValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		List<ColumnFamilyDefinition> cfDefs = new LinkedList<ColumnFamilyDefinition>();
		
		cfDefs.add(cfDefUser);
		cfDefs.add(cfDefLogin);
		cfDefs.add(cfDefAllPosts);
		cfDefs.add(cfDefSubscribedToBy);
		cfDefs.add(cfDefSubscribesTo);
		cfDefs.add(cfDefUserPosts);

		KeyspaceDefinition newKeyspace = HFactory.createKeyspaceDefinition(Keyspace,
	          ThriftKsDef.DEF_STRATEGY_CLASS,
	          replicationFactor,
	          cfDefs);
		
		
		myCluster.addKeyspace(newKeyspace, true);
		myCluster.addColumnFamily(cfDefUser, true);
		myCluster.addColumnFamily(cfDefLogin, true);
		myCluster.addColumnFamily(cfDefAllPosts, true);
		myCluster.addColumnFamily(cfDefSubscribedToBy, true);
		myCluster.addColumnFamily(cfDefSubscribesTo, true);
		myCluster.addColumnFamily(cfDefUserPosts, true);
	}
	
	private List<ColumnDefinition> createUserColumnMetadata()
	{
		List<ColumnDefinition> metadata = new LinkedList<ColumnDefinition>();
		StringSerializer stringSerializer = new StringSerializer();
		
		BasicColumnDefinition firstNameColumn = new BasicColumnDefinition();
		firstNameColumn.setName(stringSerializer.toByteBuffer("first_name"));
		firstNameColumn.setValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		BasicColumnDefinition surnameColumn = new BasicColumnDefinition();
		surnameColumn.setName(stringSerializer.toByteBuffer("surname"));
		surnameColumn.setValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		BasicColumnDefinition dobColumn = new BasicColumnDefinition();
		dobColumn.setName(stringSerializer.toByteBuffer("dob"));
		dobColumn.setValidationClass(ComparatorType.LONGTYPE.getClassName());
		
		BasicColumnDefinition emailColumn = new BasicColumnDefinition();
		emailColumn.setName(stringSerializer.toByteBuffer("email"));
		emailColumn.setValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		BasicColumnDefinition cityColumn = new BasicColumnDefinition();
		cityColumn.setName(stringSerializer.toByteBuffer("city"));
		cityColumn.setValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		BasicColumnDefinition countryColumn = new BasicColumnDefinition();
		countryColumn.setName(stringSerializer.toByteBuffer("country"));
		countryColumn.setValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		BasicColumnDefinition avatarColumn = new BasicColumnDefinition();
		avatarColumn.setName(stringSerializer.toByteBuffer("avatar"));
		avatarColumn.setValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		BasicColumnDefinition joinDateColumn = new BasicColumnDefinition();
		joinDateColumn.setName(stringSerializer.toByteBuffer("join_date"));
		joinDateColumn.setValidationClass(ComparatorType.LONGTYPE.getClassName());
		
		BasicColumnDefinition bioColumn = new BasicColumnDefinition();
		bioColumn.setName(stringSerializer.toByteBuffer("bio"));
		bioColumn.setValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		metadata.add(firstNameColumn);
		metadata.add(surnameColumn);
		metadata.add(dobColumn);
		metadata.add(emailColumn);
		metadata.add(cityColumn);
		metadata.add(countryColumn);
		metadata.add(avatarColumn);
		metadata.add(joinDateColumn);
		metadata.add(bioColumn);
		
		return metadata;
	}
	
	private List<ColumnDefinition> createLoginColumnMetadata()
	{
		List<ColumnDefinition> metadata = new LinkedList<ColumnDefinition>();
		StringSerializer stringSerializer = new StringSerializer();
		
		BasicColumnDefinition passwordColumn = new BasicColumnDefinition();
		passwordColumn.setName(stringSerializer.toByteBuffer("password"));
		passwordColumn.setValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		metadata.add(passwordColumn);
		
		return metadata;
	}
	
	private List<ColumnDefinition> createPostsColumnMetadata()
	{
		List<ColumnDefinition> metadata = new LinkedList<ColumnDefinition>();
		StringSerializer stringSerializer = new StringSerializer();
		
		BasicColumnDefinition fullNameColumn = new BasicColumnDefinition();
		fullNameColumn.setName(stringSerializer.toByteBuffer("full_name"));
		fullNameColumn.setValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		BasicColumnDefinition bodyColumn = new BasicColumnDefinition();
		bodyColumn.setName(stringSerializer.toByteBuffer("body"));
		bodyColumn.setValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		BasicColumnDefinition tagsColumn = new BasicColumnDefinition();
		tagsColumn.setName(stringSerializer.toByteBuffer("tags"));
		tagsColumn.setValidationClass(ComparatorType.UTF8TYPE.getClassName());
		
		metadata.add(fullNameColumn);
		metadata.add(bodyColumn);
		metadata.add(tagsColumn);
		
		return metadata;
	}
}
