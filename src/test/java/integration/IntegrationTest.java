package integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.springBoot.database.SpringBootDatabaseApplication;
import com.springBoot.database.model.SpringBootDataModel;
import com.springBoot.database.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootDatabaseApplication.class})
@AutoConfigureMockMvc
public class IntegrationTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private PersonRepository repository;
	
	@Before
	public void clearDB() {
		repository.deleteAll();
	}
	
	@Test
	public void RetrievePersonFromDatabase() throws Exception {
		repository.save(new SpringBootDataModel("Tom","Michigan Point", 2));
		mvc.perform(get("/api/person")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content()
			.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name", is("Tom")));
	}
	
	@Test
	public void addPersonToDatabaseTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/api/person")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\" : \"Robert\", \"address\" : \"Atlantis\", \"age\" : 200}"))
			.andExpect(status()
			.isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))	
			.andExpect(jsonPath("$.name", is("Robert")));
	}
	
	@Test
	public void deletePersonFromDatabaseTest() throws Exception {
		SpringBootDataModel testModel = new SpringBootDataModel("Test", "blah", 5);
		repository.save(testModel);
		mvc.perform(MockMvcRequestBuilders.delete("/api/person/" + testModel.getId())
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		mvc.perform(MockMvcRequestBuilders.get("/api/person/" + testModel.getId())
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
	
	
	@Test
	public void modifyPersonFromDatabaseTest() throws Exception {
		SpringBootDataModel testModel = new SpringBootDataModel("Test", "Will be edited", 4);
		repository.save(testModel);
		mvc.perform(MockMvcRequestBuilders.put("/api/person/" + testModel.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.content("{\"name\" : \"Robert\", \"address\" : \"Atlantis\", \"age\" : 200}"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))	
			.andExpect(jsonPath("$.name", is("Robert")));
	}
}
