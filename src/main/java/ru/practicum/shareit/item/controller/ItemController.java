package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader("X-Sharer-User-id") long userId) {
        log.info("Получен запрос GET /items");
        return itemService.readAllByUserId(userId);
    }

    @GetMapping("/{id}")
    public ItemDto get(@RequestHeader("X-Sharer-User-id") Long userId, @PathVariable Long id) {
        log.info("Получен запрос GET /items/{}", id);
        return itemService.getItemById(id);
    }

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-id") long userId, @Valid @RequestBody ItemDto itemDto) {
        log.info("Получен запрос POST /items");
        Item item = ItemMapper.toItem(itemDto);
        return itemService.create(item, userId);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@RequestHeader("X-Sharer-User-id") long userId, @Valid @PathVariable Long id,
                          @RequestBody ItemDto itemDto) {
        log.info("Получен запрос PUT /items");
        Item item = ItemMapper.toItem(itemDto);
        return itemService.update(id, item, userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader("X-Sharer-User-id") long userId, @Valid @PathVariable Long id) {
        log.info("Получен запрос DELETE /items");
        itemService.delete(id, userId);
    }

    @GetMapping("/search")
    private List<ItemDto> searching(@RequestParam String text) {
        return itemService.findItemsByText(text);
    }
}
