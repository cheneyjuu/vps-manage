package com.swiftcode.service;

import com.swiftcode.domain.Combo;
import com.swiftcode.repository.ComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author chen
 */
@Service
public class ComboService {
    private final ComboRepository comboRepository;

    @Autowired
    public ComboService(ComboRepository comboRepository) {
        this.comboRepository = comboRepository;
    }

    public Optional<Combo> findById(Long id) {
        return comboRepository.findById(id);
    }
}
