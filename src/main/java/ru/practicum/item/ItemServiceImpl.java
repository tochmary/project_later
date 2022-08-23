package ru.practicum.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.item.dao.ItemRepository;
import ru.practicum.item.dto.AddItemRequest;
import ru.practicum.item.model.Item;
import ru.practicum.item.model.ItemInfoWithUrlState;
import ru.practicum.item.model.QItem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UrlMetaDataRetriever urlMetaDataRetriever;

    @Override
    public List<ItemDto> getItems(long userId) {
        List<Item> userItems = repository.findByUserId(userId);
        return ItemMapper.mapToItemDto(userItems);
    }

    @Override
    @Transactional
    public ItemDto addNewItem(Long userId, AddItemRequest request) {
        // Собираем метаданные о переданном url-адресе
        UrlMetaDataRetriever.UrlMetadata result = urlMetaDataRetriever.retrieve(request.getUrl());

        // проверяем, возможно такой url-адрес уже был сохранён
        // если адрес уже есть, то обновляем информацию о тегах и возвращаем обновлённый item
        // в противном случае - сохраняем новый item и возвращаем его
        Optional<Item> maybeExistingItem = repository.findByUserIdAndResolvedUrl(userId, result.getResolvedUrl());
        Item item;
        if(maybeExistingItem.isEmpty()) {
            item = repository.save(ItemMapper.mapToItem(result, userId, request.getTags()));
        } else {
            item = maybeExistingItem.get();
            if(request.getTags() != null && !request.getTags().isEmpty()) {
                item.getTags().addAll(request.getTags());
                repository.save(item);
            }
        }
        return ItemMapper.mapToItemDto(item);
    }

    @Override
    @Transactional
    public void deleteItem(long userId, long itemId) {
        repository.deleteByUserIdAndId(userId, itemId);
    }

    @Override
    public List<ItemInfoWithUrlState> getUserItemStates(long userId) {
        return repository.findAllByUserIdWithUrlState(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> getItems(long userId, Set<String> tags) {
        // Для поиска ссылок используем QueryDSL чтобы было удобно настраивать разные варианты фильтров
        QItem item = QItem.item;
        BooleanExpression byUserId = item.userId.eq(userId);
        BooleanExpression byAnyTag = item.tags.any().in(tags);
        Iterable<Item> foundItems = repository.findAll(byUserId.and(byAnyTag));
        return ItemMapper.mapToItemDto((List<Item>) foundItems);
    }
}
