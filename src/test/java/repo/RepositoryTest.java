package repo;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.springBoot.database.repository.PersonRepository;
import com.springBoot.database.SpringBootDatabaseApplication;
import com.springBoot.database.model.SpringBootDataModel;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringBootDatabaseApplication.class})
@DataJpaTest
public class RepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PersonRepository myRepo;

	@Test
	public void retrieveByIdTest() {
		SpringBootDataModel model1 = new SpringBootDataModel("Tom", "Michigan Point", 21);
		entityManager.persist(model1);
		entityManager.flush();
		assertTrue(myRepo.findById(model1.getId()).isPresent());
	}
}
