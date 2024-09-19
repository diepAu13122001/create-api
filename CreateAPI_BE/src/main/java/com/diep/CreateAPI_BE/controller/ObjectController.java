package com.diep.CreateAPI_BE.controller;

import com.diep.CreateAPI_BE.entity.MyObject;
import com.diep.CreateAPI_BE.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("objects")
public class ObjectController {

    @Autowired
    ObjectService objectService;

    // create (POST) -------------------------------------------------------
    // Thêm nhiều đối tượng (List<MyObject>) và không trả về giá trị
    @PostMapping(value = "/addAll", params = "collectionId")
    public void addObjects(@RequestParam Long collectionId, @RequestBody List<MyObject> myObjects) {
        objectService.addObjects(collectionId, myObjects);
    }

    // Thêm một đối tượng và trả về đối tượng đã thêm
    @PostMapping
    public MyObject addObject(@RequestParam Long collectionId, @RequestBody MyObject myObject) {
        return objectService.addObject(collectionId, myObject);
    }

    // read (GET) -------------------------------------------------------
    // Lấy đối tượng theo ID
    @GetMapping(params = "id")
    public MyObject getObjectById(@RequestParam Long id) {
        return objectService.getObjectById(id);
    }

    // Lấy đối tượng theo danh sách khóa (keys)
    @PostMapping(value = "/getObjectsByKeys")
    public List<MyObject> getObjectsByKeys(@RequestBody List<String> keys) {
        System.out.println("abc");
//            return null;
        return objectService.getObjectsByKeys(keys);
    }

    // update (PATCH) -------------------------------------------------------
    // Cập nhật đối tượng và trả về đối tượng đã cập nhật
    @PatchMapping
    public MyObject updateObject(@RequestParam Long collectionId, @RequestBody MyObject myObject) {
        return objectService.updateObject(collectionId, myObject);
    }

    // delete (DELETE) -------------------------------------------------------
    // Xóa đối tượng theo ID
    @DeleteMapping
    public void deleteObjectById(@RequestParam Long collectionId, @RequestParam Long id) {
        objectService.deleteObjectById(collectionId, id);
    }

    // other -------------------------------------------------------


}
