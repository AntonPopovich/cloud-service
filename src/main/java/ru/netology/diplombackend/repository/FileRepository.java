package ru.netology.diplombackend.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.diplombackend.entity.FileEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends CrudRepository<FileEntity, Long> {

    @Query(value = "SELECT * FROM files LIMIT :limit", nativeQuery = true)
    List<FileEntity> getFiles(int limit);

    @Query(value = "DELETE FROM FILES F WHERE F.filename=:filename", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteByFilename(@Param("filename") String filename);

    Optional<FileEntity> findByFilename(String filename);

}
