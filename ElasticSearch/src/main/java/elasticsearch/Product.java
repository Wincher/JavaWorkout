package elasticsearch;

import lombok.Data;

@Data
public class Product {

	private String sku;
	private String name;
	private double price;

	public Product() {}

	public Product(String sku, String name, double price) {
		this.sku = sku;
		this.name = name;
		this.price = price;
	}
}
