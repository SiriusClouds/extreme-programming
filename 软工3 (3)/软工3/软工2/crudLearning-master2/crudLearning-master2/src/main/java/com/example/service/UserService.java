package com.example.service;

import com.example.dao.UserRepository;
import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    // 收藏用户
    public void favoriteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("用户不存在"));
        user.setIsFavorite(true);  // 设置为收藏
        userRepository.save(user);
    }

    // 取消收藏用户
    public void unfavoriteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("用户不存在"));
        user.setIsFavorite(false);  // 取消收藏
        userRepository.save(user);
    }

    // 批量收藏用户
    public void favoriteUsers(List<Long> ids) {
        userRepository.updateIsFavoriteByIdIn(ids, true);  // 调用批量更新收藏状态的方法
    }

    // 批量取消收藏用户
    public void unfavoriteUsers(List<Long> ids) {
        userRepository.updateIsFavoriteByIdIn(ids, false);  // 调用批量更新收藏状态的方法
    }

    // 查询所有收藏的用户
    public List<User> findAllFavoriteUsers() {
        return userRepository.findByIsFavorite(true);  // 查询所有收藏的用户
    }

    // 保存用户（包括收藏状态和联系方式）
    public void save(User user) {
        // 如果没有提供联系方式，生成默认的随机联系方式
        if (user.getContacts() == null || user.getContacts().isEmpty()) {
            user.setContacts("[{\"type\":\"phone\",\"value\":\"" + generateRandomPhone() + "\"}]"); // 默认一个随机联系方式
        }
        userRepository.save(user);
    }

    // 删除用户
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    // 查找单个用户
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // 查找所有用户
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // 分页查询用户
    public Page<User> findPage(Integer pageNum, Integer pageSize, String name) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return userRepository.findByNameLike(name == null ? "%" : "%" + name + "%", pageable);
    }

    // 生成随机电话
    private String generateRandomPhone() {
        String[] prefix = {"132", "133", "134", "135", "136", "137", "138", "139", "150", "151"};
        String randomPrefix = prefix[(int) (Math.random() * prefix.length)];
        String randomNumber = String.valueOf((int) (Math.random() * 1000000000L)); // 生成9位随机数字
        return randomPrefix + randomNumber.substring(0, 8); // 拼接成一个有效电话
    }
}
