package com.kamilmarnik.talkerr.comment.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;

class InMemoryCommentRepository implements CommentRepository {

  Map<Long, Comment> values = new HashMap<>();

  @Override
  public List<Comment> findAll() {
    return null;
  }

  @Override
  public List<Comment> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Comment> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Comment> findAllById(Iterable<Long> iterable) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(Long commentId) {
    values.remove(commentId);
  }

  @Override
  public void delete(Comment comment) {

  }

  @Override
  public void deleteAll(Iterable<? extends Comment> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public Comment save(Comment value) {
    if(value.getCommentId() == null || value.getCommentId() == 0) {
      value = value.toBuilder().commentId(new Random().nextLong()).build();
    }
    values.put(value.getCommentId(), value);

    return value;
  }

  @Override
  public <S extends Comment> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<Comment> findById(Long commentId) {
    return Optional.ofNullable(values.get(commentId));
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends Comment> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<Comment> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Comment getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends Comment> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Comment> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Comment> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Comment> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Comment> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Comment> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public void deleteCommentsByPostId(long postId) {
    List<Comment> comments = values.values().stream()
        .filter(comment -> comment.getPostId() == postId)
        .collect(Collectors.toList());
    values.values().removeAll(comments);
  }

  @Override
  public void deleteCommentsByPostIdIn(Set<Long> postsIds) {
    List<Comment> comments = values.values().stream()
        .filter(comment -> postsIds.contains(comment.getPostId()))
        .collect(Collectors.toList());
    values.values().removeAll(comments);
  }
}
