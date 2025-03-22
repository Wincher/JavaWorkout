package elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author wincher
 * <p> cn.wincher.es <p>
 * Reference: <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/getting-started-java.html">Eastic Official Guide</a>
 */
public class OfficialGuideDemo {
	public static void main(String[] args) throws IOException {
		// URL and API key
		String serverUrl = "http://cvm00:9200";
		String apiKey = "VnVhQ2ZHY0JDZGJrU...";

		// Create the low-level client
		HttpHeaders httpHeaders = null;
		RestClient restClient = RestClient
				.builder(HttpHost.create(serverUrl))
//				.setDefaultHeaders(new Header[]{new BasicHeader("Authorization", "ApiKey " + apiKey)})
				.build();
		// Create the transport with a Jackson mapper
		ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
		// And create the API client
		ElasticsearchClient esClient = new ElasticsearchClient(transport);

		//create index
		esClient.indices().create(c -> c.index("products"));
		indexingDocument(esClient);
		getDocuments(esClient);
		searchDocument(esClient);
		updateDocument(esClient);
		esClient.delete(d -> d.index("products").id("bk-1"));
		esClient.indices().delete(d -> d.index("products"));
		//check result with http://cvm00:9200/products/_search
		// Close the client, also closing the underlying transport object and network connections.
		esClient.close();

	}

	private static void updateDocument(ElasticsearchClient esClient) throws IOException {
		Product product = new Product("bk-1", "City bike", 128.0);

		esClient.update(u -> u
						.index("products")
						.id("bk-1")
						.upsert(product)
						.doc(Map.of("price", 128)),
				Product.class
		);
	}

	private static void searchDocument(ElasticsearchClient esClient) throws IOException {
		String searchText = "bike";
		SearchResponse<Product> response = esClient.search(s -> s
						.index("products")
						.query(q -> q
								.match(t -> t
										.field("name")
										.query(searchText)
								)
						),
				Product.class
		);
		List<Hit<Product>> hits = response.hits().hits();
		System.out.println("search hits: " + hits);
		for (Hit<Product> hit : hits) {
			Product product = hit.source();  //  获取 Product 对象 (文档)

			if (product != null) {
				System.out.println("Product ID: " + hit.id());  //  文档 ID
				System.out.println("Product Name: " + product.getName());  // 产品名称
				System.out.println("Product Price: " + product.getPrice());  // 产品价格
				System.out.println("-----------------------");
			} else {
				System.out.println("Could not retrieve product source");
			}
		}

	}

	private static void getDocuments(ElasticsearchClient esClient) throws IOException {
		GetResponse<Product> response = esClient.get(g -> g
						.index("products")
						.id("bk-1"),
				Product.class
		);

		if (response.found()) {
			Product product = response.source();
			System.out.println("Product name " + Objects.requireNonNull(product).getName());
		} else {
			System.out.println("Product not found");
		}
	}

	private static void indexingDocument(ElasticsearchClient esClient) throws IOException {
		Product product = new Product("bk-1", "City bike", 123.0);
		IndexResponse response = esClient.index(i -> i
				.index("products")
				.id(product.getSku())
				.document(product)
		);
		Product product1 = new Product("bk-2", "City bike 2", 123.0);
		esClient.index(i -> i
				.index("products")
				.id(product1.getSku())
				.document(product1)
		);
		System.out.println(("Indexed with version " + response.version()));
	}
}
