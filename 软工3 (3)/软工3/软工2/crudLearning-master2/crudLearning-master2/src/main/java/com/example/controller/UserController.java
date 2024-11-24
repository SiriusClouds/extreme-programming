package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    // 新增用户
    @PostMapping
    public Result add(@RequestBody User user) {
        userService.save(user);  // 调用 service 保存用户
        return Result.success();
    }

    // 修改用户
    @PutMapping
    public Result update(@RequestBody User user) {
        userService.save(user);  // 调用 service 保存用户
        return Result.success();
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);  // 删除用户
    }

    // 根据id查询用户
    @GetMapping("/{id}")
    public Result<User> findById(@PathVariable Long id) {
        return Result.success(userService.findById(id));  // 根据 id 查询用户
    }

    // 查询所有用户
    @GetMapping
    public Result<List<User>> findAll() {
        return Result.success(userService.findAll());  // 查询所有用户
    }

    // 分页查询用户
    @GetMapping("/page")
    public Result<Page<User>> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       @RequestParam(required = false) String name) {
        return Result.success(userService.findPage(pageNum, pageSize, name));  // 分页查询用户
    }

    // 查询所有收藏的用户
    @GetMapping("/favorites")
    public Result<List<User>> findAllFavorites() {
        return Result.success(userService.findAllFavoriteUsers());  // 查询收藏的用户
    }

    // 收藏用户
    @PostMapping("/{id}/favorite")
    public Result favorite(@PathVariable Long id) {
        userService.favoriteUser(id);  // 收藏用户
        return Result.success();
    }

    // 取消收藏用户
    @PostMapping("/{id}/unfavorite")
    public Result unfavorite(@PathVariable Long id) {
        userService.unfavoriteUser(id);  // 取消收藏用户
        return Result.success();
    }

    // 批量收藏用户
    @PostMapping("/favorite")
    public Result favoriteUsers(@RequestBody List<Long> ids) {
        userService.favoriteUsers(ids);  // 批量收藏
        return Result.success();
    }

    // 批量取消收藏用户
    @PostMapping("/unfavorite")
    public Result unfavoriteUsers(@RequestBody List<Long> ids) {
        userService.unfavoriteUsers(ids);  // 批量取消收藏
        return Result.success();
    }
}
