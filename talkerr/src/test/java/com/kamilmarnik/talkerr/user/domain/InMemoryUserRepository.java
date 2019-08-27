package com.kamilmarnik.talkerr.user.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {

  Map<Long, User> values = new ConcurrentHashMap<>();

  @Override
  public List<User> findAll() {
    return null;
  }

  @Override
  public List<User> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<User> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<User> findAllById(Iterable<Long> iterable) {
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
  public void delete(User user) {

  }

  @Override
  public void deleteAll(Iterable<? extends User> iterable) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public User save(User value) {
    if(value.getUserId() == null) {
      value = value.toBuilder().userId(new Random().nextLong()).build();
    }
    values.put(value.getUserId(), value);

    return value;
  }

  @Override
  public <S extends User> List<S> saveAll(Iterable<S> iterable) {
    return null;
  }

  @Override
  public Optional<User> findById(Long userId) {
    return Optional.ofNullable(values.get(userId));
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends User> S saveAndFlush(S s) {
    return null;
  }

  @Override
  public void deleteInBatch(Iterable<User> iterable) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public User getOne(Long aLong) {
    return null;
  }

  @Override
  public <S extends User> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends User> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends User> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends User> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public Optional<User> findUserByLogin(String login) {
    return Optional.ofNullable(values.get(login));
  }
}
