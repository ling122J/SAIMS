package com.example.mapper;

import com.example.entity.authority.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleMapper {
    @Insert("insert into role values (#{rid},#{roleCode},#{roleName})")
    int insertOne(Role role);

    @Insert("<script>" +
            "insert into role values "+
            "<trim suffixOverrides =','>"+
            "<foreach collection='list' item='item'>"+
            "(#{item.rid},#{item.roleCode},#{item.roleName}),"+
            "</foreach>"+
            "</trim>"+
            "</script>")
    int insertMany(@Param("list") List<Role> list);

    @Delete("delete from role where rid = #{rid}")
    int deleteRole(@Param("rid") Integer rid);

    @Insert("<script>" +
            "INSERT INTO user_role (user_id, role_id) VALUES " +
            "<foreach collection='map' index='userId' item='roleIds' separator=','>" +
            "<foreach collection='roleIds' item='roleId' separator=','>" +
            "(#{userId}, #{roleId})" +
            "</foreach>" +
            "</foreach>" +
            "</script>")
    int bindRoles(@Param("map") Map<Integer, List<Integer>> map);

    @Insert("<script>" +
            "INSERT INTO user_role (role_id,user_id) VALUES " +
            "<foreach collection='map' index='roleId' item='userIds' separator=','>" +
            "<foreach collection='userIds' item='userId' separator=','>" +
            "(#{roleId}, #{userId})" +
            "</foreach>" +
            "</foreach>" +
            "</script>")
    int bindUsers(@Param("map") Map<Integer, List<Integer>> map);

    @Select("<script>" +
            "select * from role "+
            "<where>"+
            "   <if test='role.rid != \"\" and role.rid != null'>"+
            "       rid = #{role.rid}"+
            "   </if>" +
            "   <if test='role.roleCode != \"\" and role.roleCode != null'>"+
            "       AND role_code = #{role.roleCode}"+
            "   </if>" +
            "   <if test='role.roleName != \"\" and role.roleName != null'>"+
            "       AND role_name = #{role.roleName}"+
            "   </if>" +
            "</where>"+
            "limit #{size} offset #{page}"+
            "</script>")
    List<Role> findByConditions(@Param("role") Role role,@Param("page") Integer page,@Param("size") Integer size);

    //关联查询用户的角色列表
    @Select("select role.* from role " +
            "inner join user_role on role_id = rid " +
            "inner join users on user_id = uid " +
            "where uid = #{uid}")
    List<Role> findRoles(@Param("uid") Integer uid);

    @Select("SELECT user_id FROM user_role WHERE role_id = #{rid}")
    List<Integer> findUserIdsByRoleId(Integer rid);

    @Select("SELECT COUNT(*) FROM role")
    int selectTotalCount();

    @Update("update role set rid = #{rid},role_code = #{roleCode}," +
            "role_name = #{roleName} where rid = #{rid}")
    int updateRole(Role role);

}
