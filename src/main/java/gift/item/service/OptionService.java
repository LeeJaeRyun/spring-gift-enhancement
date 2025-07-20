package gift.item.service;

import gift.global.exception.CustomException;
import gift.global.exception.ErrorCode;
import gift.item.dto.OptionResponseDto;
import gift.item.entity.Option;
import gift.item.repository.OptionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionService {
    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    //ItemId로 Options들 가져오는거
    @Transactional(readOnly = true)
    public List<OptionResponseDto> findOptionsByItemId(Long productId) {
        List<Option> options = optionRepository.findByItemId(productId);
        return options.stream()
                .map(OptionResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    //동일한 상품 내의 중복된 옵션 이름 검사
    public void validateDuplicateOptionName(Long productId, String optionName) {
        boolean exists = optionRepository.existsByItemIdAndName(productId, optionName);
        if (exists) {
            throw new CustomException(ErrorCode.OPTION_NAME_DUPLICATE);
        }
    }


}
