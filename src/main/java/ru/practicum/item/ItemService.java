package ru.practicum.item;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

interface ItemService {

    ItemDto addNewItem(long userId, ItemDto itemDto);

    List<ItemDto> getItems(long userId);

    void deleteItem(long userId, long itemId);

    List<ItemInfoWithUrlState> getUserItemStates(long userId);

    @Transactional(readOnly = true)
    List<ItemDto> getItems(long userId, Set<String> tags);
}
