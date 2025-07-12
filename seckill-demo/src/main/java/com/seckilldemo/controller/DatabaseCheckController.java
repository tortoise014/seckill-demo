package com.seckilldemo.controller;

import com.seckilldemo.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库检查控制器
 */
@RestController
@RequestMapping("/db")
public class DatabaseCheckController {

    @Autowired
    private DataSource dataSource;

    /**
     * 检查数据库连接状态
     */
    @GetMapping("/check")
    public Result<Map<String, Object>> checkDatabase() {
        Map<String, Object> result = new HashMap<>();
        
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            
            result.put("connected", true);
            result.put("databaseName", metaData.getDatabaseProductName());
            result.put("databaseVersion", metaData.getDatabaseProductVersion());
            result.put("url", metaData.getURL());
            result.put("username", metaData.getUserName());
            
            return Result.success(result, "数据库连接成功");
            
        } catch (Exception e) {
            result.put("connected", false);
            result.put("error", e.getMessage());
            return Result.failed("数据库连接失败: " + e.getMessage());
        }
    }

    /**
     * 查看所有表
     */
    @GetMapping("/tables")
    public Result<List<String>> showTables() {
        List<String> tables = new ArrayList<>();
        
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            
            ResultSet resultSet = statement.executeQuery("SHOW TABLES");
            while (resultSet.next()) {
                tables.add(resultSet.getString(1));
            }
            
            return Result.success(tables, "查询表列表成功，共找到 " + tables.size() + " 个表");
            
        } catch (Exception e) {
            return Result.failed("查询表列表失败: " + e.getMessage());
        }
    }

    /**
     * 查看user表结构
     */
    @GetMapping("/user-table")
    public Result<Map<String, Object>> checkUserTable() {
        Map<String, Object> result = new HashMap<>();
        
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            
            // 检查表是否存在
            ResultSet tableCheck = statement.executeQuery(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'seckill' AND table_name = 'user'"
            );
            tableCheck.next();
            boolean tableExists = tableCheck.getInt(1) > 0;
            result.put("tableExists", tableExists);
            
            if (tableExists) {
                // 查看表结构
                ResultSet columns = statement.executeQuery("DESCRIBE user");
                List<Map<String, Object>> columnInfo = new ArrayList<>();
                while (columns.next()) {
                    Map<String, Object> column = new HashMap<>();
                    column.put("field", columns.getString("Field"));
                    column.put("type", columns.getString("Type"));
                    column.put("null", columns.getString("Null"));
                    column.put("key", columns.getString("Key"));
                    column.put("default", columns.getString("Default"));
                    columnInfo.add(column);
                }
                result.put("columns", columnInfo);
                
                // 查看数据行数
                ResultSet count = statement.executeQuery("SELECT COUNT(*) FROM user");
                count.next();
                result.put("rowCount", count.getInt(1));
                
                // 查看前几条数据
                ResultSet data = statement.executeQuery("SELECT id, nickname FROM user LIMIT 5");
                List<Map<String, Object>> rows = new ArrayList<>();
                while (data.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("id", data.getLong("id"));
                    row.put("nickname", data.getString("nickname"));
                    rows.add(row);
                }
                result.put("sampleData", rows);
            }
            
            return Result.success(result);
            
        } catch (Exception e) {
            return Result.failed("检查user表失败: " + e.getMessage());
        }
    }
} 