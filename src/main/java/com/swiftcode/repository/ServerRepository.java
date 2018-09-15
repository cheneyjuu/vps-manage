package com.swiftcode.repository;

import com.swiftcode.domain.Server;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chen
 */
public interface ServerRepository extends JpaRepository<Server, Long> {
}
