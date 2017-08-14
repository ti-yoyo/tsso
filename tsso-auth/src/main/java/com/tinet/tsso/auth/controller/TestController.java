package com.tinet.tsso.auth.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
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

import com.tinet.tsso.auth.entity.Test;
import com.tinet.tsso.auth.param.TestParam;
import com.tinet.tsso.auth.service.TestService;

/**
 * 测试用Controller
 * 
 * @date 2017-07-28
 * @author wangll
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

	@Autowired
	private TestService testService;

	@GetMapping
	public ResponseEntity<Object> search(TestParam param) {

		List<Test> testList = testService.search(param);

		return ResponseEntity.ok().body(testList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> get(@PathVariable Integer id) {

		Test test = testService.get(id);

		return ResponseEntity.ok().body(test);
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody TestParam testParam) {

		Test test = new Test();
		BeanUtils.copyProperties(testParam, test);

		testService.create(test);

		return ResponseEntity.status(HttpStatus.CREATED).body(test);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody TestParam testParam) {

		Test test = new Test();
		BeanUtils.copyProperties(testParam, test);
		test.setId(id);

		testService.update(test);

		return ResponseEntity.ok().body(test);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Integer id) {

		testService.delete(id);

		return ResponseEntity.ok().build();
	}
}
