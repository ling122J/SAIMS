package com.example.mapper;

import com.example.entity.authority.Account;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface AccountMapper {
    @Insert("insert into users values (#{uid},#{username},#{password},#{realName}," +
            "#{enabled},#{email},#{createTime},#{lastLogin})")
    int insertOne(Account account);

    @Insert("<script>" +
            "insert into users values" +
            "<trim suffixOverrides = ','>" +
            "<foreach collection='list' item='item'>" +
            "(#{item.uid},#{item.username},#{item.password},#{item.realName}," +
            "#{item.enabled},#{item.email},#{item.createTime},#{item.lastLogin})," +
            "</foreach>" +
            "</trim>" +
            "</script>")
    int insertMany(@Param("list") List<Account> list);

    @Delete("delete from users where uid=#{uid}")
    int deleteUser(@Param("uid") Integer uid);

    @Select("select * from users where uid = #{uid}")
    Account findUid(@Param("uid") Integer uid);

    @Select("select * from users where username = #{username}")
    Account findByUsername(@Param("username") String username);

    @Select("<script>" +
            "select * from users " +
            "<where>" +
            "   <if test='account.uid != \"\" and account.uid != null'>" +
            "       AND uid = #{account.uid}" + "</if>" +
            "   <if test='account.username != \"\" and account.username != null'>" +
            "       AND username = #{account.username}" + "</if>" +
            "   <if test='account.password != \"\" and account.password != null'>" +
            "       AND password = #{account.password}" + "</if>" +
            "   <if test='account.realName != \"\" and account.realName != null'>" +
            "       AND real_name = #{account.realName}" + "</if>" +
            "   <if test='account.enabled != \"\" and account.enabled != null'>" +
            "       AND enabled = #{account.enabled}" + "</if>" +
            "   <if test='account.email != \"\" and account.email != null'>" +
            "       AND email = #{account.email}" + "</if>" +
            "   <if test='account.createTime != \"\" and account.createTime != null'>" +
            "       AND create_time = #{account.createTime}" + "</if>" +
            "   <if test='account.lastLogin != \"\" and account.lastLogin != null'>" +
            "       AND last_login = #{account.lastLogin}" + "</if>" +
            "</where>" +
            "limit #{size} offset #{page}" +
            "</script>")
    List<Account> findByConditions(@Param("account") Account account, @Param("page") Integer page, @Param("size") Integer size);

    @Select("SELECT COUNT(*) FROM users")
    int selectTotalCount();

    @Update("update users set " +
            "uid = #{uid},username = #{username},password = #{password},real_name = #{realName}," +
            "enabled = #{enabled},email = #{email},create_time = #{createTime},last_login = #{lastLogin}" +
            "where uid = #{uid}")
    int updateUser(Account account);

}