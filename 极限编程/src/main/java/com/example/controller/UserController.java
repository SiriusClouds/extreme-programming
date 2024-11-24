package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/import")
    public ResponseEntity<String> importUsers(@RequestParam("file") MultipartFile file) {
        try {
            userService.importUsersFromExcel(file);
            return ResponseEntity.ok("通讯录导入成功");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("导入失败: " + e.getMessage());
        }
    }
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportUsers() throws IOException {
        byte[] excelFile = userService.exportUsersToExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=users.xlsx");

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(excelFile);
    }
    @PostMapping
    public Result add(@RequestBody User user) {
        userService.save(user);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody User user) {
        userService.save(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    public Result<User> findById(@PathVariable Long id) {
        return Result.success(userService.findById(id));
    }

    @GetMapping
    public Result<List<User>> findAll() {
        return Result.success(userService.findAll());
    }

    @GetMapping("/page")
    public Result<Page<User>> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       @RequestParam(required = false) String name) {
        return Result.success(userService.findPage(pageNum, pageSize, name));
    }

    @GetMapping("/favorites")
    public Result<List<User>> findAllFavorites() {
        return Result.success(userService.findAllFavoriteUsers());
    }

    @PostMapping("/{id}/favorite")
    public Result favorite(@PathVariable Long id) {
        userService.favoriteUser(id);
        return Result.success();
    }

    @PostMapping("/{id}/unfavorite")
    public Result unfavorite(@PathVariable Long id) {
        userService.unfavoriteUser(id);
        return Result.success();
    }

    @PostMapping("/favorite")
    public Result favoriteUsers(@RequestBody List<Long> ids) {
        userService.favoriteUsers(ids);
        return Result.success();
    }
    @PostMapping("/unfavorite")
    public Result unfavoriteUsers(@RequestBody List<Long> ids) {
        userService.unfavoriteUsers(ids);
        return Result.success();
    }
}
