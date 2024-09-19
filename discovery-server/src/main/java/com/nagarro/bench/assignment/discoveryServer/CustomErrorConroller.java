package com.nagarro.bench.assignment.discoveryServer;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CustomErrorController implements ErrorController {

	private static final String ERROR_MAPPING = "/error";

	@RequestMapping(ERROR_MAPPING)
	public ResponseEntity<Void> error() {
		return ResponseEntity.notFound().build();
	}
	
}
