package gift.item.service;

import gift.global.exception.CustomException;
import gift.global.exception.ErrorCode;
import gift.item.constant.ItemConstants;
import gift.item.dto.CreateItemDto;
import gift.item.dto.ItemDto;
import gift.item.dto.UpdateItemDto;
import gift.item.entity.Item;
import gift.item.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public ItemDto saveItem(CreateItemDto dto) {
        validateKeyword(dto.getName());
        Item item = new Item(dto.getName(), dto.getPrice(), dto.getImageUrl());
        Item savedItem = itemRepository.save(item);
        return new ItemDto(savedItem);
    }

    @Transactional(readOnly = true)
    public ItemDto findItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
        return new ItemDto(item);
    }

    @Transactional(readOnly = true)
    public Page<ItemDto> findAllItems(Pageable pageable) {
        return itemRepository.findAll(pageable)
                .map(ItemDto::new);
    }

    @Transactional
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    @Transactional
    public void updateItem(Long id, UpdateItemDto dto) {
        validateKeyword(dto.getName());
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
        item.update(dto.getName(), dto.getPrice(), dto.getImageUrl());
    }

    private void validateKeyword(String name) {
        if (name.contains(ItemConstants.BLOCKED_KEYWORD_KAKAO)) {
            throw new CustomException(ErrorCode.ITEM_KEYWORD_INVALID);
        }
    }

}
