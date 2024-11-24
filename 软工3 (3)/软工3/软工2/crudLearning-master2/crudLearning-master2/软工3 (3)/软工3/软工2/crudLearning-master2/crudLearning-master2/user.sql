-- 创建用户表
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,     -- 用户ID
                      name VARCHAR(255) NOT NULL,                -- 用户名
                      age INT,                                  -- 用户年龄
                      sex VARCHAR(10),                          -- 用户性别
                      address VARCHAR(255),                     -- 用户地址
                      phone VARCHAR(20),                        -- 用户电话
                      create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 创建时间
                      is_favorite BOOLEAN DEFAULT FALSE         -- 是否收藏
);

-- 插入初始数据
INSERT INTO user (name, age, sex, address, phone, is_favorite)
VALUES ('张三', 25, '男', '北京市朝阳区', '1234567890', FALSE);

INSERT INTO user (name, age, sex, address, phone, is_favorite)
VALUES ('李四', 30, '女', '上海市浦东新区', '0987654321', TRUE);

INSERT INTO user (name, age, sex, address, phone, is_favorite)
VALUES ('王五', 35, '男', '广州市天河区', '1122334455', FALSE);

-- 可选：为name和is_favorite字段创建索引
CREATE INDEX idx_name ON user(name);
CREATE INDEX idx_is_favorite ON user(is_favorite);
ALTER TABLE user ADD contacts JSON DEFAULT NULL;
