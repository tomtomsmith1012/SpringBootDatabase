package com.springBoot.database.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.database.exception.ResourceNotFoundException;
import com.springBoot.database.model.Order;
import com.springBoot.database.repository.OrderRepository;
import com.springBoot.database.repository.PersonRepository;

@RestController
public class PersonController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PersonRepository myRepo;

	@GetMapping("/person/{personId}/orders")
	public Page<Order> getAllOrderPeopleById(@PathVariable(value = "person_id") Long PersonId, Pageable pageable) {
		return orderRepository.findByPersonId(PersonId, pageable);
	}

	@PostMapping("/person/{personId}/orders")
	public Order createComment(@PathVariable(value = "personId") Long personId, @Valid @RequestBody Order order) {
		return myRepo.findById(personId).map(SpringBootDataModel -> {
			order.setPerson(SpringBootDataModel);
			return orderRepository.save(order);
		}).orElseThrow(() -> new ResourceNotFoundException("Person", "id", order));
	}

	@PutMapping("/person/{personId}/orders/{orderId}")
	public Order updateOrder(@PathVariable(value = "personId") Long personId,
			@PathVariable(value = "orderId") Long orderId, @Valid @RequestBody Order orderRequest) {
		if (!myRepo.existsById(personId)) {
			throw new ResourceNotFoundException("Person ", "id", orderRequest);
		}
		return orderRepository.findById(orderId).map(order -> {
			order.setTitle(orderRequest.getTitle());
			return orderRepository.save(order);
		}).orElseThrow(() -> new ResourceNotFoundException("OrderId", "id", orderRequest));
	}

	@DeleteMapping("/person/{personId}/orders/{orderId}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "personId") Long personId,
			@PathVariable(value = "orderId") Long orderId) {
		if (!myRepo.existsById(personId)) {
			throw new ResourceNotFoundException("Person ", "id", personId);
		}
		return orderRepository.findById(orderId).map(order -> {
			orderRepository.delete(order);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("OrderId ", orderId.toString(), null));
	}
}
