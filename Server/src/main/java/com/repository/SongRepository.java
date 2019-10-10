package com.repository;

import org.springframework.data.repository.CrudRepository;

import com.model.Song;

public interface SongRepository extends CrudRepository<Song, Integer> {

}