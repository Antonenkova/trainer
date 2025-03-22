package ru.antonenkova.storage;

import org.springframework.stereotype.Repository;
import ru.antonenkova.domain.model.OpenQuestionCard;
import ru.antonenkova.domain.repo.QuestionRepository;

import java.util.*;

@Repository
public class QuestionInMemoryStorage implements QuestionRepository {
    private final Map<Long, OpenQuestionCard> storage = new HashMap<>();

    @Override
    public List<OpenQuestionCard> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<OpenQuestionCard> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void add(OpenQuestionCard task) {
        storage.put(task.getId(), task);
    }

    @Override
    public void update(OpenQuestionCard task) {
        storage.replace(task.getId(), task);
    }

    @Override
    public void remove(Long id) {
        storage.remove(id);
    }
}

