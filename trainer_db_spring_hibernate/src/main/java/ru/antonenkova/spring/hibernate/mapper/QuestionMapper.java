package ru.antonenkova.spring.hibernate.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.antonenkova.domain.model.OpenQuestionCard;
import ru.antonenkova.spring.hibernate.entity.OpenQuestionCardEntity;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "question", source = "question")
    @Mapping(target = "expectedAnswer", source = "expectedAnswer")
    OpenQuestionCard toModel(OpenQuestionCardEntity entity);

    @InheritInverseConfiguration
    OpenQuestionCardEntity toEntity(OpenQuestionCard model);
}