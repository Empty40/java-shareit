package ru.practicum.shareit.request.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.ItemRequestValidator;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.List;

@Slf4j
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class RequestService {

    private final UserServiceImpl userServiceImpl;
    private final ItemRequestValidator requestValidator;
    private final ItemRequestRepository requestRepository;

    @Transactional
    public ItemRequest addRequest(int requesterId, ItemRequest request) {
        log.info("Добавление запроса вещи от пользователя с id: " + requesterId);
        userServiceImpl.findUserById(requesterId);
        request.setRequesterId(requesterId);
        requestValidator.requestValidateAndSetCreationTime(request);

        return requestRepository.save(request);
    }

    public List<ItemRequest> getRequestsForUser(int requesterId) {
        log.info("Просмотр заявок на вещи от пользователя с id: " + requesterId);
        userServiceImpl.findUserById(requesterId);

        return requestValidator.formAnswersListForUser(requesterId);
    }

    public List<ItemRequest> getOtherUsersRequests(Integer requesterId, Integer startPage, Integer outputSize) {
        userServiceImpl.findUserById(requesterId);
        log.info("Просмотр заявок всех кроме пользователя с id: " + requesterId);

        return requestValidator.getOtherUsersRequestsPaginated(requesterId, startPage, outputSize);
    }

    public ItemRequest getInformationAboutSingleRequests(int requesterId, int requestId) {
        log.info("Просмотр заявки с id: " + requestId);
        userServiceImpl.findUserById(requesterId);
        return requestValidator.getInformationAboutSingleRequests(requestId);
    }
}