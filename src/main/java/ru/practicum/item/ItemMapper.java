package ru.practicum.item;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getUserId(),
                item.getUrl(),
                new HashSet<>(item.getTags())
        );
    }

    public static List<ItemDto> toItemDto(List<Item> itemList) {
        return itemList.stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public static Item toItem(ItemDto itemDto, long userId) {
        return new Item(
                null,
                userId,
                itemDto.getUrl(),
                itemDto.getTags()
                );
    }
}
