package com.kamilmarnik.talkerr.topic.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTopicRepository implements TopicRepository{
  private Map<Long, Topic> values = new ConcurrentHashMap<>();

  @Override
  public List<Topic> findAll() {
    return new ArrayList<>(values.values());
  }

  @Override
  public List<Topic> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Topic> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Topic> findAllById(Iterable<Long> iterable) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(Long topicId) {
    values.remove(topicId);
  }

  @Override
  public void delete(Topic topic) {

  }

  @Override
  public void deleteAll(Iterable<? extends Topic> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public Topic save(Topic value) {
    if(value.getTopicId() == null || value.getTopicId() == 0) {
      value = value.toBuilder().topicId(new Random().nextLong()).build();
    }
    values.put(value.getTopicId(), value);

    return value;
  }

  @Override
  public <S extends Topic> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Topic> findById(Long topicId) {
    return Optional.ofNullable(values.get(topicId));
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends Topic> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<Topic> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Topic getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends Topic> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Topic> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Topic> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Topic> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Topic> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Topic> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public Optional<Topic> findByName(String name) {
    return values.values().stream()
        .filter(topic -> topic.getName().equals(name))
        .findFirst();
  }
}
