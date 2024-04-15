package KWU.likelion.GoldBalance.controller;

import KWU.likelion.GoldBalance.dto.response.VoteResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/default")
public interface VoteOperations {

    @PostMapping("/{id}/{selectNum}")
    ResponseEntity<String> vote(@PathVariable String id, @PathVariable String selectNum);
    @GetMapping("/{id}")
    ResponseEntity<VoteResult> submitVoteResult(@PathVariable String id);
}
