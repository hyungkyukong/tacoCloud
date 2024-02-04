package tacos;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

//@SpringBootTest
@Slf4j
class TacoCloudApplicationTests {

	private long counter;
	private void wasCalled() {
		counter++;
	}
	public static class Product {
		int amount = 0;
		String name = "";

		public Product(int amount, String name) {
			this.amount = amount;
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public Integer getAmount() {
			return this.amount;
		}

	}


	@Test
	void contextLoads() {
		List<String> nullList = null;
		collectionToStream(nullList)
				.filter(str -> str.contains("a"))
				.map(String::length)
				.forEach(a -> {log.info("devlog a : " + a);}); //[]

	}

	public <T> Stream<T> collectionToStream(Collection<T> collection) {
		return Optional
				.ofNullable(collection)
				.map(Collection::stream)
				.orElseGet(Stream::empty);
	}


}
