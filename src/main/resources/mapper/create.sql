CREATE TABLE train_basic_info (
id BIGINT(32)  NOT NULL PRIMARY KEY AUTO_INCREMENT,
train_no VARCHAR(20) NOT NULL COMMENT '车次',
start_station_name VARCHAR(20) NOT NULL COMMENT '始发站',
end_station_name VARCHAR(20) NOT NULL COMMENT '终点站',
depart_station_name VARCHAR(20) NOT NULL COMMENT '出发站',
dest_station_name VARCHAR(20) NOT NULL COMMENT '目的站',
distance INT(10) NOT NULL COMMENT '距离',
rws DECIMAL(9,2) NOT NULL COMMENT '软卧上铺价格',
rwx DECIMAL(9,2) NOT NULL COMMENT '软卧下铺价格',
yws DECIMAL(9,2) NOT NULL COMMENT '硬卧上铺价格',
ywz DECIMAL(9,2) NOT NULL COMMENT '硬卧中铺价格',
ywx DECIMAL(9,2) NOT NULL COMMENT '硬卧下铺价格',
add_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
INDEX idx_train_no_depart_station_name_dest_station_name (train_no,depart_station_name,dest_station_name)
);