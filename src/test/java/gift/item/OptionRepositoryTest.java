package gift.item;

import gift.item.entity.Item;
import gift.item.entity.Option;
import gift.item.repository.ItemRepository;
import gift.item.repository.OptionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OptionRepositoryTest {

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("itemId로 한 아이템에 대한 옵션들 목록 조회")
    void findByItemId() {
        //given
        Item item = new Item("바지", 1000, "pants.com");
        itemRepository.save(item);

        Option option1 = new Option("레전드", 1, item);
        Option option2 = new Option("유니크", 5, item);
        optionRepository.saveAll(List.of(option1, option2));

        //when
        List<Option> found = optionRepository.findByItemId(item.getId());

        //then
        assertThat(found).hasSize(2);
        assertThat(found).extracting("name")
                .containsExactlyInAnyOrder("레전드", "유니크");
    }

    @Test
    @DisplayName("itemId와 옵션명으로 옵션 존재 여부 확인")
    void existsByItemIdAndName() {
        //given
        Item item = new Item("바지", 1000, "pants.com");
        itemRepository.save(item);

        Option option = new Option("레전드", 2, item);
        optionRepository.save(option);

        //when
        boolean exists = optionRepository.existsByItemIdAndName(item.getId(), "레전드");
        boolean notExists = optionRepository.existsByItemIdAndName(item.getId(), "레어");

        //then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

}
