package wu.justin;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

public class JsonPathTest {
	
	// it comes from  https://github.com/jayway/JsonPath
	@Test
	public void testJson(){		
		
		String json ="{store: "
				+ "{    book: ["
				+ "        {            "
				+ "category: 'reference',            "
				+ "author: 'Nigel Rees',            "
				+ "title: 'Sayings of the Century',"
				+ "            price: 8.95"
				+ "        },"
				+ "        {"
				+ "            category: 'fiction',"
				+ "            author: 'J. R. R. Tolkien',"
				+ "            title: 'The Lord of the Rings',"
				+ "            isbn: '0-395-19395-8',"
				+ "            price: 22.99"
				+ "        }    ],"
				+ "    bicycle: {"
				+ "        color: 'red',"
				+ "        price: 19.95"
				+ "    }"
				+ "},"
				+ " expensive: 10"
				+ "}";
		
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
		
		String author0 = JsonPath.read(document, "$.store.book[0].author");
		List<String> category = JsonPath.read(document, "store.book[?(@.author=='Nigel Rees')].category");

		System.out.println("author0= " + author0);
		System.out.println("category= " + category);
		
		assertEquals(author0, "Nigel Rees");

		assertEquals(category.size(), 1);
		assertEquals(category.get(0), "reference");

	}
}
