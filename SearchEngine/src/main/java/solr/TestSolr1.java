package solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @author wincher
 * @date   22/11/2017.
 */
public class TestSolr1 {
	
	private static final String URL = "http://192.168.0.111:8080/solr";
	
	/**
	 * 修改数据，可以根据id直接自动覆盖掉旧的内容信息
	 * @throws Exception
	 */
	@Test
	public void testAdd() throws Exception {
		//实例化solr对象
		SolrServer solrServer = new HttpSolrServer(URL);
		//实例化添加数据类
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.setField("id", "1001");
		doc1.setField("name", "iphone X");
		doc1.setField("price", "8000");
		doc1.setField("url", "/images/001.jpg");
		
		SolrInputDocument doc2 = new SolrInputDocument();
		doc2.setField("id", "1002");
		doc2.setField("name", "SAMSUNG S8+");
		doc2.setField("price", "6999");
		doc2.setField("url", "/images/002.jpg");
		//设置服务器保存信息冰提交
		solrServer.add(doc1);
		solrServer.add(doc2);
		
		solrServer.commit();
		
	}
	
	@Test
	public void testSearch() throws Exception {
		SolrServer solrServer = new HttpSolrServer(URL);
		//查询类
		SolrQuery solrQuery = new SolrQuery();
		//查询关键词
		solrQuery.set("q", "id:1001");
		//查询数据
		QueryResponse response = solrServer.query(solrQuery);
		//获取数据
		SolrDocumentList solrList = response.getResults();
		long num = solrList.getNumFound();
		System.out.println("条数:" + num);
		for (SolrDocument sd : solrList) {
			String id = (String) sd.get("id");
			String name = (String) sd.get("name");
			String price = (String) sd.get("price_c");
			String url = (String) sd.get("url");
			System.out.println("id: " + id + "name: " + name + "price: " + price + "url: " + url);
		}
	}
	
	@Test
	public void testDel() throws Exception {
		SolrServer solrServer = new HttpSolrServer(URL);
		
		solrServer.deleteById("1");
		solrServer.deleteByQuery("id:zhangsan id:1002");
		solrServer.commit();
	}
}
