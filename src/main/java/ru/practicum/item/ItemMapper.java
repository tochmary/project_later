package ru.practicum.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
/*
    public static List<ItemDto> toItemDto(Iterable<Item> items) {
        List<ItemDto> dtos = new ArrayList<>();
        for (Item item : items) {
            dtos.add(toItemDto(item));
        }
        return dtos;
    }*/

    public static Item toItem(ItemDto itemDto, long userId) {
        Item item = new Item();
        item.setUserId(userId);
        item.setUrl(itemDto.getUrl());
        item.setTags(itemDto.getTags());
        return item;
    }
}
