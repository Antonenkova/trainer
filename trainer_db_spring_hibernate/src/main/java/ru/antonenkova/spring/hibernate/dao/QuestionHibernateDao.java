package ru.antonenkova.spring.hibernate.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.antonenkova.domain.model.OpenQuestionCard;
import ru.antonenkova.domain.repo.QuestionRepository;
import ru.antonenkova.spring.hibernate.entity.OpenQuestionCardEntity;
import ru.antonenkova.spring.hibernate.mapper.QuestionMapper;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@Primary
public class QuestionHibernateDao implements QuestionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final QuestionMapper mapper;

    public QuestionHibernateDao(QuestionMapper mapper) {
        System.out.println("QuestionHibernateDao");
        this.mapper = mapper;
    }

    @Override
    public List<OpenQuestionCard> findAll() {
        return entityManager.createQuery("SELECT q FROM OpenQuestionCardEntity q", OpenQuestionCardEntity.class)
                .getResultList()
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public Optional<OpenQuestionCard> findById(Long id) {
        OpenQuestionCardEntity entity = entityManager.find(OpenQuestionCardEntity.class, id);
        return Optional.ofNullable(entity).map(mapper::toModel);
    }

    @Override
    public void add(OpenQuestionCard task) {
        entityManager.persist(mapper.toEntity(task));
    }

    @Override
    public void update(OpenQuestionCard task) {
        entityManager.merge(mapper.toEntity(task));
    }

    @Override
    public void remove(Long id) {
        OpenQuestionCardEntity entity = entityManager.find(OpenQuestionCardEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
