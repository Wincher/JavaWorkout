package solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

/**
 * @author wincher
 * @date   22/11/2017.
 */
public class TestSolr2 {
	private static final String URL = "http://192.168.0.111:8080/solr";
	private SolrServer server;
	
	@Before
	public void init() {
		server = new HttpSolrServer(URL);
	}
	
	@After
	public void close() {
		server = null;
		System.runFinalization();
		System.gc();
	}
	
	@Test
	public void testAddUser() throws Exception {
		String prefix = "user_";
		
		User u1 = new User();
		u1.setId(prefix + UUID.randomUUID().toString().substring(4).substring(prefix.length()));
		u1.setName("张三");
		u1.setAge(25);
		u1.setSex("male");
		u1.setLike(new String[]{"football", "basketball", "tennis"});
		this.server.addBean(u1);
		
		User u2 = new User();
		u2.setId(prefix + UUID.randomUUID().toString().substring(4).substring(prefix.length()));
		u2.setName("李思");
		u2.setAge(20);
		u2.setSex("female");
		u2.setLike(new String[]{"shopping", "movie", "table tennis"});
		this.server.addBean(u2);
		
		this.server.commit();
	}
	
	@Test
	public void testChange() throws Exception {
		SolrDocument doc = new SolrDocument();
		doc.addField("id", "123456");
		doc.addField("user_name", "名称");
		doc.addField("user_like", new String[]{"music", "book", "sport"});
		doc.put("user_sex", "female");
		doc.addField("user_age", 18);
		User u = this.server.getBinder().getBean(User.class, doc);
		System.out.println(u);
	}
	
	@Test
	public void testSearchUser() throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.set("q","user_name:张三");
		QueryResponse response = this.server.query(solrQuery);
		SolrDocumentList solrList = response.getResults();
		long num = solrList.getNumFound();
		System.out.println("count: " + num);
		for (SolrDocument sd : solrList) {
			User u = this.server.getBinder().getBean(User.class, sd);
			System.out.println(u.getId());
			System.out.println(u.getName());
		}
	}
	
	@Test
	public void testDelUser() throws IOException, SolrServerException {
		server.deleteByQuery("user_name:张三");
		server.commit();
	}
}
