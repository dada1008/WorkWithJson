package com.dada.json;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dada.json.dto.Order;
import com.dada.json.dto.Product;
import com.dada.json.dto.SearchResult;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkWithJsonApplicationTests {

	@SuppressWarnings("unchecked")
	@Test
	public void testJacksonGenericObjectToJson() {
		SearchResult<Product> result = new SearchResult<>();
		List<Product> items = new ArrayList<>();
		items.add(new Product(1, "p1"));
		items.add(new Product(2, "p2"));
		items.add(new Product(3, "p3"));
		result.setItems(items);
		result.setError(false);
		result.setSuccess(true);
		result.setSize(items.size());
		result.setMessage("successful");
		File product_json_file = new File("product_search.json");
		try {
			//JsonToObjectConverter.writeToFile(product_json_file, result);
			SearchResult<Product> jsonResult = JsonToObjectConverter.jsonToParametricObject(product_json_file, SearchResult.class, Product.class);
			assertEquals(result,jsonResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testJacksonGenericObjectListToJson() {
		SearchResult<List<Order>> result = new SearchResult<>();
		List<List<Order>> items = new ArrayList<>();
		items.add(Arrays.asList(new Order(11, "Order11",110), new Order(12, "Order12",120)));
		items.add(Arrays.asList(new Order(21, "Order21",210), new Order(22, "Order22",220)));
		items.add(Arrays.asList(new Order(31, "Order31",310), new Order(32, "Order32",320)));
		result.setItems(items);
		result.setError(false);
		result.setSuccess(true);
		result.setSize(items.size());
		result.setMessage("successful");
		File order_json_file = new File("order_search.json");
		try {
			//JsonToObjectConverter.writeToFile(order_json_file, result);
			SearchResult<List<Order>> jsonResult = JsonToObjectConverter.jsonToParametricObjectCollection(order_json_file, SearchResult.class, List.class, Order.class);
			assertEquals(result,jsonResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
