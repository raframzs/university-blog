package com.raframz.universityblog.application.usecase;

import com.raframz.universityblog.application.in.ICurseQuery;
import com.raframz.universityblog.application.out.ICurseRepository;
import com.raframz.universityblog.domain.Curse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CurseUseCase implements ICurseQuery {

    private final ICurseRepository repository;

    public CurseUseCase(ICurseRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Curse> findAllCurses() {
        return repository.findAll();
    }
}
