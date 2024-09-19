package com.diep.CreateAPI_BE.service;

import com.diep.CreateAPI_BE.entity.MyCollection;
import com.diep.CreateAPI_BE.entity.MyObject;
import com.diep.CreateAPI_BE.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ObjectService {
    @Autowired
    ObjectRepository objectRepository;

    @Autowired
    CollectionService collectionService;

    public void addObjects(Long collectionId, List<MyObject> myObjects) {
        MyCollection collection = this.collectionService.getCollectionById(collectionId);
        List<MyObject> existingList = collection.getObjects();
        existingList.addAll(myObjects);
        this.collectionService.updateObjectList(collectionId, existingList);
        this.objectRepository.saveAll(myObjects);
    }

    public MyObject addObject(Long collectionId, MyObject myObject) {
        MyCollection collection = this.collectionService.getCollectionById(collectionId);
        List<MyObject> existingList = collection.getObjects();
        existingList.add(myObject);
        this.collectionService.updateObjectList(collectionId, existingList);
        return this.objectRepository.save(myObject);
    }

    public MyObject getObjectById(Long id) {
        return this.objectRepository.findById(id).get();
    }

    public List<MyObject> getObjectsByKeys(List<String> keys) {
        // get only shared collections
        List<MyCollection> sharedCollections = this.collectionService.getSharedCollections();
        List<MyObject> objectsByKeys = new ArrayList<>();
        for (MyCollection collection : sharedCollections) {
            for (MyObject object : collection.getObjects()) {
                // Convert the Set of keys to a List
                List<String> keyList = new ArrayList<>(object.getData().keySet());
                if (keyList.containsAll(keys)) {
                    objectsByKeys.add(object);
                }
            }
        }
        return objectsByKeys;
    }

    public MyObject updateObject(Long collectionId, MyObject myObject) {
        return this.objectRepository.save(myObject);
    }

    public void deleteObjectById(Long collectionId, Long id) {
        MyObject myObject = this.getObjectById(id);
        // update in collection
        MyCollection collection = this.collectionService.getCollectionById(collectionId);
        List<MyObject> existingList = collection.getObjects();
        existingList.remove(myObject);
        this.collectionService.updateObjectList(collectionId, existingList);
        this.objectRepository.delete(myObject);
    }
}
