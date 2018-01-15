package wang.condon.read.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.condon.read.entity.Content;

public interface ContentRepository extends JpaRepository<Content, String> {
}