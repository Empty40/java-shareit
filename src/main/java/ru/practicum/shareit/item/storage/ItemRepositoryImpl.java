package ru.practicum.shareit.item.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.EntityNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items;

    private final Map<Long, List<Item>> userItems;
    private static long itemId;

    public ItemRepositoryImpl() {
        items = new HashMap<>();
        userItems = new HashMap<>();
    }

    private long generateId() {
        return ++itemId;
    }

    @Override
    public Item create(Item item, User user) {
        item.setId(generateId());
        item.setOwner(user);

        userItems.computeIfAbsent(user.getId(), k -> new ArrayList<>()).add(item);

        items.put(item.getId(), item);
        return items.get(item.getId());
    }

    @Override
    public List<Item> readAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public List<Item> readAllByUserId(Long id) {
        return userItems.get(id);
    }

    @Override
    public Item update(Long id, Item item, User user) {
        checkItem(id);
        return items.get(id);
    }

    @Override
    public Item getItemById(Long id) {
        checkItem(id);
        return items.get(id);
    }

    @Override
    public void delete(Long id) {
        checkItem(id);
        items.remove(id);
    }

    @Override
    public List<Item> findItemsByText(String text) {
        return readAll()
                .stream()
                .filter(item -> ((
                        item.getName().toLowerCase().contains(text.toLowerCase())
                                || item.getDescription().toLowerCase().contains(text.toLowerCase()))
                        && item.getAvailable()))
                .collect(Collectors.toList());
    }

    private void checkItem(Long itemId) {
        if (!items.containsKey(itemId)) {
            if (items.get(itemId) == null) {
                log.warn("item с id = {}, не найден!", itemId);
                throw new EntityNotFoundException("Предмет отсутствует в списке!");
            }
        }
    }
}
