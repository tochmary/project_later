package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;

    @Override
    public List<ItemDto> getItems(long userId) {
        List<Item> items = repository.findByUserId(userId);
        return ItemMapper.toItemDto(items);
    }

    @Override
    public ItemDto addNewItem(long userId, ItemDto itemDto) {
        Item item = repository.save(ItemMapper.toItem(itemDto,userId));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public void deleteItem(long userId, long itemId) {
        repository.deleteByUserIdAndItemId(userId, itemId);
    }
}
