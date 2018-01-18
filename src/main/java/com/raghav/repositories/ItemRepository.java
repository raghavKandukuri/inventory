package com.raghav.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raghav.inventory.models.Item;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

}
