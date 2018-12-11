package com.springBoot.database.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.database.exception.ResourceNotFoundException;
import com.springBoot.database.model.SpringBootDataModel;
import com.springBoot.database.repository.PersonRepository;

@RestController
@RequestMapping("/api")
public class SpringBootController {

	@Autowired
	PersonRepository myRepository;
	
	@PostMapping("/person")
	public SpringBootDataModel createPerson(@Valid @RequestBody SpringBootDataModel mSDM) {
		return myRepository.save(mSDM);
	}
	@GetMapping("/person/{id}")
	public SpringBootDataModel getPersonByID(@PathVariable(value = "id")Long personID) {
		return myRepository.findById(personID).orElseThrow(()-> new ResourceNotFoundException("SpringBootDataModel", "id", personID));
	}
	@GetMapping("/person")
	public List<SpringBootDataModel> getAllPeople() {
		return myRepository.findAll();
	}
	@PutMapping("/person/{id}")
	public SpringBootDataModel updatePerson(@PathVariable(value = "id")Long personID, @Valid @RequestBody SpringBootDataModel personDetails) {
		SpringBootDataModel mSDM = myRepository.findById(personID).orElseThrow(() -> new ResourceNotFoundException("Person", "id", personID));
		
		mSDM.setName(personDetails.getName());
		mSDM.setAddress(personDetails.getAddress());
		mSDM.setAge(personDetails.getAge());
		
		SpringBootDataModel updateData = myRepository.save(mSDM);
		return updateData;
	}
	@DeleteMapping("/person/{id}")
	public ResponseEntity<?> deletePerson(@PathVariable(value = "id")Long personID) {
		SpringBootDataModel mSDM = myRepository.findById(personID).orElseThrow(() -> new ResourceNotFoundException("Person", "id", personID));
		myRepository.delete(mSDM);
		return ResponseEntity.ok().build();
	}
}
