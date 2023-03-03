package com.raframz.universityblog.application.in;

import com.raframz.universityblog.domain.Curse;

import java.util.List;

public interface ICurseQuery {
    List<Curse> findAllCurses();
}
