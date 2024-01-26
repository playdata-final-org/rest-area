package com.example.DevSculpt.dao.file;

import com.example.DevSculpt.entity.FileEntity;
import com.example.DevSculpt.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FileDAOImpl implements FileDAO {
    private final FileRepository repository;

    @Override
    public FileEntity save(FileEntity file) {
        return repository.save(file);
    }
}
