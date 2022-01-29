package com.misobuild.domain.weather;

import org.springframework.data.repository.CrudRepository;

// TODO 다른 레파지토리들 키값으로 id가 들어가야 하는데 왜 String이 들어갔지!
public interface CurrentAirRedisRepository extends CrudRepository<CurrentAir, Long> {
}