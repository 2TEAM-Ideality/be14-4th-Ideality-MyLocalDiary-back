package com.leesang.mylocaldiary.search;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.leesang.mylocaldiary.common.response.CommonResponseVO;

import jakarta.annotation.PostConstruct;

@Service
public class SearchService {

	@Value("${naver.search.client-id}")
	private String clientId;

	@Value("${naver.client-secret}")
	private String clientSecret;

	@PostConstruct
	public void init() {
		System.out.println("✅ clientId = " + clientId);
		System.out.println("✅ clientSecret = " + clientSecret);
	}

	private final String NAVER_LOCAL_SEARCH_URL = "https://openapi.naver.com/v1/search/local.json";

	public ResponseEntity<CommonResponseVO<?>> searchLocal(String query) {

		RestTemplate restTemplate = new RestTemplate();

		// 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id", clientId);
		headers.set("X-Naver-Client-Secret", clientSecret);

		// 파라미터 설정
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("query", query);
		params.add("display", "5"); // 검색 결과 개수 (선택)

		// 요청 생성
		HttpEntity<?> entity = new HttpEntity<>(headers);

		// URL에 쿼리스트링 붙이기
		String requestUrl = NAVER_LOCAL_SEARCH_URL + "?query=" + query + "&display=5";

		// 요청 실행
		ResponseEntity<Map> response = restTemplate.exchange(
			requestUrl,
			HttpMethod.GET,
			entity,
			Map.class
		);

		// 응답 처리
		return ResponseEntity.ok(CommonResponseVO.builder()
			.status(200)
			.message("검색 성공")
			.data(response.getBody().get("items")) // ✅ 응답 결과 그대로 반환
			.build());
	}
}