package com.raframz.universityblog.application.out;

import com.raframz.universityblog.domain.Curse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICurseRepository extends JpaRepository<Curse, Integer> {
}
