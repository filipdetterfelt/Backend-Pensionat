package com.example.backendpensionat.Repos;

import com.example.backendpensionat.Models.Queue;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface QueueRepo extends CrudRepository<Queue, UUID> {

}
