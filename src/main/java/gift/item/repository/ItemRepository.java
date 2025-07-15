package gift.item.repository;

import gift.item.dto.UpdateItemDto;
import gift.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    
}
