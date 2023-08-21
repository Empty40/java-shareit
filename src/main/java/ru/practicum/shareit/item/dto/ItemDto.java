package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ItemDto {
    private Long id;
    @NotBlank(message = "name не может быть пустым!")
    private String name;
    @NotBlank(message = "description не может быть пустым!")
    private String description;
    @NotNull(message = "available не может быть пустым!")
    private Boolean available;
}
