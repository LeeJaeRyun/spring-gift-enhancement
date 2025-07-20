package gift.item.service;

import gift.item.dto.OptionResponseDto;
import gift.item.entity.Option;
import gift.item.repository.OptionRepository;
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

    @Transactional(readOnly = true)
    public List<OptionResponseDto> findOptionsByItemId(Long productId) {
        List<Option> options = optionRepository.findByItemId(productId);
        return options.stream()
                .map(OptionResponseDto::fromEntity)
                .collect(Collectors.toList());
    }


}
