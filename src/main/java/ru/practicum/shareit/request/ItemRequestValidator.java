package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.DataBaseException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.RequestAnswer;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.request.repository.RequestAnswerRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemRequestValidator {

    private final ItemRequestRepository itemRequestRepository;
    private final RequestAnswerRepository requestAnswerRepository;

    public void requestValidateAndSetCreationTime(ItemRequest request) {
        String text = request.getDescription();
        if (text == null || text.isBlank()) {
            throw new ValidationException("Введен пустой комментарий, id пользователя: " + request.getRequesterId());
        }
        request.setCreated(LocalDateTime.now());
    }

    public List<ItemRequest> formAnswersListForUser(int requesterId) {
        List<ItemRequest> userRequests = itemRequestRepository
                .findAllByRequesterIdOrderByCreatedDesc(requesterId);

        for (ItemRequest i : userRequests) {
            int requestId = i.getId();
            List<RequestAnswer> answersForRequest = requestAnswerRepository
                    .findAllByRequestId(requestId);
            i.setItems(answersForRequest);
        }
        return userRequests;
    }

    public ItemRequest getInformationAboutSingleRequests(int requestId) {
        ItemRequest request = itemRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new NotFoundException("Не найдена заявка с id: " + requestId));
        List<RequestAnswer> answersForRequest = requestAnswerRepository
                .findAllByRequestId(requestId);
        request.setItems(answersForRequest);

        return request;
    }

    public List<ItemRequest> getOtherUsersRequestsPaginated(int requesterId, Integer startPage, Integer outputSize) {
        if (outputSize == null || outputSize <= 0) {
            throw new DataBaseException("Количество объектов, подлежащих выводу на одной странице, должно быть положительным.");
        }
        if (startPage < 0) {
            throw new DataBaseException("Номер начальной страницы не может быть отрицательным.");
        }

        PageRequest pageRequest = PageRequest.of(startPage, outputSize);

        List<ItemRequest> userRequests = itemRequestRepository
                .findAllByRequesterIdIsNotOrderByCreatedDesc(requesterId, pageRequest);

        for (ItemRequest i : userRequests) {
            int requestId = i.getId();
            List<RequestAnswer> answersForRequest = requestAnswerRepository
                    .findAllByRequestId(requestId);
            i.setItems(answersForRequest);
        }
        return userRequests;
    }
}