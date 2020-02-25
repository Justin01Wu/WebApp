package wu.justin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

public class JsonPathTest {
	
	private static String json ="{store: "
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
	
	// it comes from  https://github.com/jayway/JsonPath
	@Test
	public void testJson(){
		
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
		
		String author0 = JsonPath.read(document, "$.store.book[0].author");
		List<String> category = JsonPath.read(document, "store.book[?(@.author=='Nigel Rees')].category");

		System.out.println("author0= " + author0);
		System.out.println("category= " + category);
		
		assertEquals(author0, "Nigel Rees");

		assertEquals(category.size(), 1);
		assertEquals(category.get(0), "reference");
		
	}
	
	
	public static DocumentContext getJsonPathDocumentContext(String json) {
		Configuration config = Configuration
				.defaultConfiguration()
				.addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
		
		DocumentContext dc = JsonPath.using(config).parse(json);
		return dc;

	}
	
	// it comes from  https://stackoverflow.com/questions/38449267/optional-jsonpath-using-jayway
	@Test
	public void testNullReturnValue(){
		
		DocumentContext dc = getJsonPathDocumentContext(json);
		String author2 = dc.read( "$.store.book[0].author2");
		
		assertNull(author2);
		
		String book2 = dc.read( "$.store.book2");
		
		assertNull(book2);

	}

}
