package com.leesang.mylocaldiary.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leesang.mylocaldiary.common.response.CommonResponseVO;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/naver")
public class SearchController {

	private final SearchService searchService;



	@Autowired
	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}

	@GetMapping("/search")
	public ResponseEntity<CommonResponseVO<?>> search(@RequestParam String query) {

		return searchService.searchLocal(query);
	}
}
