package com.omkar.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omkar.backend.model.Associate;
import com.omkar.backend.repository.AssociateRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/")
public class AssociateController {

	@Autowired
	AssociateRepository associateRepository;
		
	// CREATE ASSOCIATE (name, m1, m2, m3, avg, total)
	@PostMapping("/add")
	public ResponseEntity<Associate> createAssociate(@RequestBody Associate associate){
		try {
			Associate assc = associateRepository.save(new Associate(associate.getName(), associate.getSubject1(), associate.getSubject2(), associate.getSubject3(), associate.getAverage(), associate.getTotal()));
			return new ResponseEntity<>(assc, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// READ ALL
	@GetMapping("/associates")
	public ResponseEntity<List<Associate>> fetchAll(@RequestParam(required = false) String title){
		try {
			List<Associate> associates = new ArrayList<>();
			associateRepository.findAll().forEach(associates::add);
			
			if(associates.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
			return new ResponseEntity<>(associates, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// UPDATE ASSOCIATE id
	@PutMapping("/update/{id}")
	public ResponseEntity<Associate> updateAssociate(@PathVariable("id") String id, @RequestBody Associate associate){
		Optional<Associate> asscData = associateRepository.findById(id);
		
		if(asscData.isPresent()) {
			Associate assc = asscData.get();
			assc.setName(associate.getName());
			assc.setSubject1(associate.getSubject1());
			assc.setSubject2(associate.getSubject2());
			assc.setSubject3(associate.getSubject3());
			assc.setAverage(associate.getAverage());
			assc.setTotal(associate.getTotal());
			return new ResponseEntity<>(associateRepository.save(assc), HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	// DELETE ASSOCIATE id
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") String id){
		try {
			associateRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
