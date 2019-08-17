package com.kamilmarnik.talkerr.post.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryPostRepository implements PostRepository {

  Map<Long, Post> values = new ConcurrentHashMap<>();

  @Override
  public List<Post> findAll() {
    return null;
  }

  @Override
  public List<Post> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Post> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Post> findAllById(Iterable<Long> iterable) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(Long aLong) {

  }

  @Override
  public void delete(Post post) {

  }

  @Override
  public void deleteAll(Iterable<? extends Post> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public Post save(Post value) {
    if(value.getPostId() == null) {
      value = value.toBuilder().userId(new Random().nextLong()).build();
    }
    values.put(value.getPostId(), value);

    return value;
  }

  @Override
  public <S extends Post> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Post> findById(Long postId) {
    return Optional.ofNullable(values.get(postId));
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends Post> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<Post> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Post getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends Post> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Post> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Post> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Post> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Post> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Post> boolean exists(Example<S> example) {
    return false;
  }
}
