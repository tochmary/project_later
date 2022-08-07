package ru.practicum.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

  /*  public ItemRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
*/
   // -> @PersistenceContext


    @Override
    public List<Item> findByUserId(long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> cr = cb.createQuery(Item.class);
        Root<Item> root = cr.from(Item.class);
        cr.select(root).where(cb.equal(root.get("userId"), userId));
        List<Item> foundItems = entityManager.createQuery(cr).getResultList();
        return foundItems;
    }

    @Override
    public Item save(Item item) {
        entityManager.persist(item);
        return item;
    }

    public Item findByUserId(long userId, long itemId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> cr = cb.createQuery(Item.class);
        Root<Item> root = cr.from(Item.class);
        cr.select(root)
                .where(cb.and(
                        cb.equal(root.get("userId"), userId),
                        cb.equal(root.get("id"), itemId))
                );
        Item item = entityManager.createQuery(cr).getSingleResult();
        return item;
    }

    @Override
    public void deleteByUserIdAndItemId(long userId, long itemId) {
        Item item = findByUserId(userId,itemId);
        entityManager.remove(item);
    }
}

/*
package ru.practicum.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {
    private long id = 1;
    private final Map<Long, List<Item>> items = new HashMap<>();

    @Override
    public List<Item> findByUserId(long userId) {
        return items.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    public Item save(Item item) {
        long idItem = generateId();
        item.setId(idItem);
        items.compute(item.getUserId(), (userId, userItems) -> {
            if (userItems == null) {
                userItems = new ArrayList<>();
            }
            userItems.add(item);
            return userItems;
        });

        log.debug("Добавлена ссылка: {}", item);
        return item;
    }

    @Override
    public void deleteByUserIdAndItemId(long userId, long itemId) {
        if (items.containsKey(userId)) {
            List<Item> userItems = items.get(userId);
            userItems.removeIf(item -> item.getId().equals(itemId));
        }
        log.debug("Удалена ссылка с id {} для userId {}", itemId, userId);
    }

    private long generateId() {
        return id++;
        /*long lastId = items.values()
                .stream()
                .flatMap(Collection::stream)
                .mapToLong(Item::getId)
                .max()
                .orElse(0);
        return lastId + 1;*/
//   }
//            }