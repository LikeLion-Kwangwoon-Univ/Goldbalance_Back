package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.dto.response.AllPostList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

public interface HomeOperations {

    @GetMapping("/home")
    ResponseEntity<Map<String, List<AllPostList>>> getHomeInfo(); //home 에 리스트 전달
}
