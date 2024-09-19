package com.diep.CreateAPI_BE.service;

import com.diep.CreateAPI_BE.dto.CollectionDto;
import com.diep.CreateAPI_BE.entity.MyCollection;
import com.diep.CreateAPI_BE.entity.MyObject;
import com.diep.CreateAPI_BE.entity.MyUser;
import com.diep.CreateAPI_BE.repository.CollectionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CollectionService {
    @Autowired
    CollectionRepository collectionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    UserService userService;

    public MyCollection addCollection(String apiKey, String name, Boolean isShared) {
        MyCollection newCollection = new MyCollection(0L, name, isShared, new ArrayList<>());
        Set<MyCollection> existingList = this.userService.getCollectionsByApiKey(apiKey);
        // check duplicate before add in list
        if (!existingList.contains(newCollection)) {
            // add in user list
            existingList.add(newCollection);
            this.userService.updateCollectionList(apiKey, existingList);
            return this.collectionRepository.save(newCollection);
        }
        return null;

    }

    public List<MyObject> getObjectsById(Long id) {
        return this.getCollectionById(id).getObjects();
    }

    public List<MyObject> getObjectsWithLimit(long id, int limit, int page) {
        TypedQuery<MyObject> query = entityManager.createQuery("SELECT objects FROM MyCollection where id=" + id, MyObject.class);
        query.setFirstResult(page * limit); // Set the offset (starting position)
        query.setMaxResults(limit);         // Set the limit (number of results to fetch)
        return query.getResultList();
    }

    public List<MyCollection> getAllCollections() {
        return this.collectionRepository.findAll();
    }

    public MyCollection getCollectionById(Long id) {
        return this.collectionRepository.findById(id).get();
    }

    // only get shared collections
    public List<MyCollection> getCollectionsByName(String name) {
        return this.collectionRepository.findByNameAndShared(name);
    }

    public List<MyCollection> getSharedCollections() {
        return this.collectionRepository.findByIsShared(true);
    }

    public MyCollection updateCollection(Long id, String name, Boolean isShared) {
        MyCollection collection = this.getCollectionById(id);
        collection.setName(name);
        collection.setIsShared(isShared);
        return this.collectionRepository.save(collection);
    }

    public void deleteCollectionById(String apiKey, Long id) {
        MyCollection collection = this.getCollectionById(id);
        Set<MyCollection> existingList = this.userService.getCollectionsByApiKey(apiKey);
        existingList.remove(collection);
        // xoa collection trong user truoc
        this.userService.updateCollectionList(apiKey, existingList);
        this.collectionRepository.deleteById(id);
    }

    public MyCollection updateObjectList(Long id, List<MyObject> objects) {
        MyCollection collection = this.getCollectionById(id);
        collection.setObjects(objects);
        return this.collectionRepository.save(collection);
    }
}
