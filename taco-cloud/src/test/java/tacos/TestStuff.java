package tacos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

public class TestStuff {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) {
		Ord ord = new Ord();
		ord.setAddress("Universitate");
		ord.setCity("Bucuresti");
		ord.setName("object-01");
		ord.setTime(new Date());
		List<String> strings = Arrays.asList("FirstElement", "SecondElement", "ThirdElement");
		ord.setStringArray(strings);

		Map<String, Object> blabla = objectMapper.convertValue(ord, Map.class);
		System.out.println(blabla);
	}

}

@Data
class Ord {

	private Date time;

	private List<String> strings = new ArrayList<>();

	@NotBlank(message = "nName is required.")
	public String name;

	@NotBlank(message = "City is required.")
	public String city;

	@NotBlank(message = "Address is required.")
	public String address;

	public void addString(String string) {
		this.strings.add(string);
	}

	public void setStringArray(List<String> strings) {
		this.strings = strings;
	}

	public List<String> getStrings() {
		return strings;
	}
}