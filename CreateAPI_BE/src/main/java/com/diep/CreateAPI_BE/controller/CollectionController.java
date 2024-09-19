package com.diep.CreateAPI_BE.controller;

import com.diep.CreateAPI_BE.dto.CollectionDto;
import com.diep.CreateAPI_BE.entity.MyCollection;
import com.diep.CreateAPI_BE.entity.MyObject;
import com.diep.CreateAPI_BE.service.CollectionService;
import com.diep.CreateAPI_BE.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("collections")
public class CollectionController {

    @Autowired
    CollectionService collectionService;

    // create (POST) -------------------------------------------------------
    // POST: Add a new collection
    @PostMapping
    public MyCollection addCollection(@RequestBody CollectionDto collectionDto) {
        return collectionService.addCollection(collectionDto.getApiKey(), collectionDto.getName(), collectionDto.getIsShared());
    }

    // read (GET) -------------------------------------------------------
    // GET: Get objects by ID
    @GetMapping("/getObjects")
    public List<MyObject> getObjectsById(@RequestParam Long id) {
        return collectionService.getObjectsById(id);
    }

    // GET: Get all collections
    @GetMapping
    public List<MyCollection> getAllCollections() {
        return collectionService.getAllCollections();
    }

    // GET: Get a collection by ID
    @GetMapping("/{id}")
    public MyCollection getCollectionById(@PathVariable Long id) {
        return collectionService.getCollectionById(id);
    }

    // GET: Get collections by name
    @GetMapping(params = "name")
    public List<MyCollection> getCollectionsByName(@RequestParam String name) {
        return collectionService.getCollectionsByName(name);
    }

    // GET: Get shared collections
    @GetMapping("/shared")
    public List<MyCollection> getSharedCollections() {
        return collectionService.getSharedCollections();
    }

    // GET: Get objects with limit and pagination
    @GetMapping(value = "/getObjectsWithLimit", params = {"id", "limit", "page"})
    public List<MyObject> getObjectsWithLimit(@RequestParam Long id, @RequestParam int limit, @RequestParam int page) {
        return collectionService.getObjectsWithLimit(id, limit, page);
    }

    // update (PATCH) -------------------------------------------------------
    // PATCH: Update a collection
    @PatchMapping
    public MyCollection updateCollection(@RequestBody CollectionDto collectionDto) {
        return collectionService.updateCollection(collectionDto.getId(), collectionDto.getName(), collectionDto.getIsShared());
    }

    // delete (DELETE) -------------------------------------------------------
    // DELETE: Delete a collection by ID
    @DeleteMapping
    public void deleteCollectionById(@RequestParam String apiKey, @RequestParam Long id) {
        collectionService.deleteCollectionById(apiKey, id);
    }

    // other -------------------------------------------------------

}
