package nextstep.waiting.controller;

import auth.annotation.AuthRequired;
import lombok.RequiredArgsConstructor;
import nextstep.member.domain.Member;
import nextstep.waiting.dto.WaitingRequest;
import nextstep.waiting.dto.WaitingResponse;
import nextstep.waiting.mapper.WaitingMapper;
import nextstep.waiting.service.WaitingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservation-waitings")
@RequiredArgsConstructor
public class WaitingController {

    private final WaitingService waitingService;

    @PostMapping
    public ResponseEntity<Void> createWaiting(@RequestBody WaitingRequest waitingRequest, @AuthRequired Member member) {
        Long id = waitingService.create(waitingRequest.getScheduleId(), member.getId());

        return ResponseEntity.created(URI.create("/reservation-waitings/" + id)).build();
    }

    @GetMapping("/mine")
    public ResponseEntity<List<WaitingResponse>> getMyWaiting(@AuthRequired Member member) {
        List<WaitingResponse> waitingResponses = waitingService.findByMemberId(member.getId()).stream()
                .map(WaitingMapper.INSTANCE::domainToResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(waitingResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMyWaiting(@PathVariable("id") Long id, @AuthRequired Member member) {
        waitingService.deleteMyWaiting(id, member.getId());

        return ResponseEntity.noContent().build();
    }
}
