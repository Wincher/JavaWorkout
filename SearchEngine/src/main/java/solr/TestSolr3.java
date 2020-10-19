package solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author wincher
 * @date   22/11/2017.
 */
public class TestSolr3 {
	private static final String URL = "http://192.168.0.111:8080/solr";
	private SolrServer server;
	private static Random r;
	
	@Before
	public void init() {
		server = new HttpSolrServer(URL);
		r = new Random();
	}
	
	@After
	public void close() {
		server = null;
		System.runFinalization();
		System.gc();
	}
	
	@Test
	public void testAddMulti() throws Exception {
		for (int i = 1; i < 21; i++) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.setField("id", i);
			doc.setField("name", "phone" + i);
			doc.setField("price", r.nextInt(10));
			doc.setField("description", "MyPhone" + i);
			this.server.add(doc);
		}
		
		for (int i = 21; i < 41; i++) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.setField("id", i);
			doc.setField("name", "computer" + i);
			doc.setField("price", r.nextInt(10));
			doc.setField("description", "MyPC" + i);
			server.add(doc);
		}
		this.server.commit();
	}
	
	@Test
	public void testSearchMulti() throws SolrServerException {
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", "*:*");
		params.set("start", 0);
		params.set("rows", 20);
		params.set("sort", "price desc");
		QueryResponse qr = server.query(params);
		SolrDocumentList sdl = qr.getResults();
		for (SolrDocument sd: sdl) {
			System.out.println(sd.get("name"));
		}
	}
	
	@Test
	public void queryCase() throws Exception {
		SolrQuery sq = new SolrQuery();
		
		//AND OR NOT CONDITION
		sq.set("q", "name:phone AND price:9");
//		sq.set("q", "name:phone OR price:9");
//		sq.set("q", "name:phone NOT price:9");
		
		//TO CONDITION
//		sq.set("q", "name:phone AND price:[6 TO 9]");

//		sq.addFilterQuery("name:computer");
		
		//显示设置
//		sq.setHighlight(true); //开启高亮组件
		sq.addHighlightField("name"); //高亮字段
		sq.setHighlightSimplePre("<font color='red'>"); //标记 高亮关键字前缀
//		sq.setHighlightSimplePost("</font>");//后缀
//		sq.setHighlightSnippets(1);//结果分片数， 默认为1
//		sq.setFacet(true)
//				.setFacetMinCount(1)
//				.setFacetLimit(5) //段
//				.addFacetField("name", "price");
		
		sq.set("start", 0);
		sq.set("end", 10);
		sq.set("sort", "price desc");
		QueryResponse qr = server.query(sq);
		SolrDocumentList sdl = qr.getResults();
		for (SolrDocument sd : sdl) {
			System.out.println("name: " + sd.get("name") + ", price: " + sd.get("price"));
		}
	}
	
	@Test
	public void testFacetQuery() throws SolrServerException, NamingException {
		SolrQuery sq = new SolrQuery("*:*");
		//Facet为solr重的层次分类查询
		sq.setFacet(true)
				.setFacetMinCount(1)
				.setFacetLimit(5) //段
				.setFacetPrefix("my") //查询description中关键词前缀是"My"的
				.addFacetField("description");//可指定多个
		
		sq.set("start", 0);
		sq.set("end", 10);
		sq.set("sort", "price desc");
		
		QueryResponse qr = server.query(sq);
		SolrDocumentList sdl = qr.getResults();
		for (SolrDocument sd : sdl) {
			System.out.println("name: " + sd.get("name") + ", price: " + sd.get("price"));
		}
		
		List<FacetField> facets = qr.getFacetFields();
		for (FacetField facet : facets) {
			List<FacetField.Count> facetCounts = facet.getValues();
			for (FacetField.Count count : facetCounts) {
				//关键字:出现次数
				System.out.println(count.getName()  + ":" + count.getCount());
				
			}
		}
		
	}
}
