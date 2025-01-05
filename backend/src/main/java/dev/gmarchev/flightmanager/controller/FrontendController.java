package dev.gmarchev.flightmanager.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

	/**
	 * Redirect all unknown routes to the React frontend
	 */
	@GetMapping("/**/{path:[^\\.]*}")
	public String redirectApi(HttpServletRequest request) {

		if (request.getRequestURI().startsWith("/api")) {

			// Let Spring handle unknown API requests, even though this would never be reached as they are set as
			// authenticated
			return null;
		}

		return "forward:/";
	}
}
