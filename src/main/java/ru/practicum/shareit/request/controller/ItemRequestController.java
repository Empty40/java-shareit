package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.request.model.ItemRequest;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final RequestService requestService;

    @PostMapping
    public ItemRequest add(@RequestHeader("X-Sharer-User-Id") int requesterId, @RequestBody ItemRequest request) {
        return requestService.addRequest(requesterId, request);
    }

    @GetMapping()
    public List<ItemRequest> getRequestsForUser(@RequestHeader("X-Sharer-User-Id") int requesterId) {
        return requestService.getRequestsForUser(requesterId);
    }

    @GetMapping("/all")
    public List<ItemRequest> getRequestsFromOther(@RequestHeader("X-Sharer-User-Id") int requesterId,
                                                  @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from,
                                                  @RequestParam(value = "size", defaultValue = "20") Integer size) {

        return requestService.getOtherUsersRequests(requesterId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequest getInformationAboutSingleRequests(@RequestHeader("X-Sharer-User-Id") int requesterId,
                                                         @PathVariable("requestId") int requestId) {
        return requestService.getInformationAboutSingleRequests(requesterId, requestId);
    }
}