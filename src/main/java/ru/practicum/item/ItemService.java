package ru.practicum.item;

import java.util.List;

interface ItemService {

    ItemDto addNewItem(long userId, ItemDto itemDto);

    List<ItemDto> getItems(long userId);

    void deleteItem(long userId, long itemId);
}
