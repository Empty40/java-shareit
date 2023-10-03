
package ru.practicum.shareit.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.model.RequestAnswer;

import java.util.List;

public interface RequestAnswerRepository extends JpaRepository<RequestAnswer, Integer>  {
    public List<RequestAnswer> findAllByRequestId(int requestId);
}


