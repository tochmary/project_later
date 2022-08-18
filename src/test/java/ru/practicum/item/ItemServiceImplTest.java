package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.config.PersistenceConfig;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@TestPropertySource(properties = {"db.name=test"})
@SpringJUnitConfig({PersistenceConfig.class, ItemServiceImpl.class})
class ItemServiceImplTest {
    private final ItemService service;

    @Test
    void getItems() {
        // when
        List<ItemDto> items = service.getItems(1);

        // then
        assertThat(items, hasSize(4));
    }
}