package ru.antonenkova.api.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.antonenkova.api.dto.OpenQuestionCardDto;
import ru.antonenkova.domain.model.OpenQuestionCard;

@Mapper(componentModel = "spring")
public interface QuestionDtoMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "question", source = "question")
    @Mapping(target = "expectedAnswer", source = "expectedAnswer")
    OpenQuestionCard toModel(OpenQuestionCardDto dto);

    @InheritInverseConfiguration
    OpenQuestionCardDto toDto(OpenQuestionCard model);
}