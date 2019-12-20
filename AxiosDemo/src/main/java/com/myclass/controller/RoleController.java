package com.myclass.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.entity.Role;
import com.myclass.repository.RoleRepository;

@RestController
@RequestMapping("api/role")
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping("")
	public Object get() {
		return new ResponseEntity<List<Role>>(roleRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public Object get(@PathVariable String id) {
		Role entity = roleRepository.findById(id).get();
		return new ResponseEntity<Role>(entity, HttpStatus.OK);
	}
	
	@PostMapping("")
	public Object post(@RequestBody Role entity) {
		try {
			entity.setId(UUID.randomUUID().toString());
			entity = roleRepository.save(entity);
			return new ResponseEntity<Role>(entity, HttpStatus.CREATED);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/{id}")
	public Object put(@PathVariable String id, @RequestBody Role entity) {
		try {
			if(!roleRepository.existsById(id))
				return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
			
			entity.setId(id);
			entity = roleRepository.save(entity);
			return new ResponseEntity<Role>(entity, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public Object delete(@PathVariable String id) {
		try {
			if(!roleRepository.existsById(id))
				return new ResponseEntity<String>("Id không tồn tại!", HttpStatus.BAD_REQUEST);
			
			roleRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
	}
}