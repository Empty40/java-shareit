package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.mapper.RequestMapper;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        return ItemDto
                .builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .request(RequestMapper.toRequestDto(item.getRequest()))
                .build();
    }

    public static Item toItem(ItemDto itemDto) {
        return Item
                .builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .request(RequestMapper.toRequest(itemDto.getRequest()))
                .build();
    }
}
