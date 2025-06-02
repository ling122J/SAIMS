package com.example.mapper;

import com.example.entity.authority.Permission;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PermissionMapper {

    @Insert("insert into permission values (#{pid},#{permissionName},#{permissionCode}," +
            "#{resourceUrl},#{resourceType},#{parentId},#{orderNo},#{icon})")
    int insertOne(Permission permission);

    @Insert("<script>" +
            "insert into permission values "+
            "<trim suffixOverrides = ','>"+
            "<foreach collection = 'list' item='item'>"+
            "(#{item.pid},#{item.permissionName},#{item.permissionCode},#{item.resourceUrl}," +
            "#{item.resourceType},#{item.parentId},#{item.orderNo},#{item.icon}),"+
            "</foreach>"+
            "</trim>"+
            "</script>")
    int insertMany(@Param("list") List<Permission> list);

    @Delete("delete from permission where pid = #{pid}")
    int deletePermission(@Param("pid") Integer pid);

    @Select("<script>" +
            "SELECT * FROM permission " +
            "<where>" +
            "   <if test='permission.pid != null and permission.pid != \"\"'>" +
            "       pid = #{permission.pid}" +
            "   </if>" +
            "   <if test='permission.permissionName != null and permission.permissionName != \"\"'>" +
            "       AND permission_name = #{permission.permissionName}" +
            "   </if>" +
            "   <if test='permission.permissionCode != null and permission.permissionCode != \"\"'>" +
            "       AND permission_code = #{permission.permissionCode}" +
            "   </if>" +
            "   <if test='permission.resourceUrl != null and permission.resourceUrl != \"\"'>" +
            "       AND resource_url = #{permission.resourceUrl}" +
            "   </if>" +
            "   <if test='permission.resourceType != null and permission.resourceType != \"\"'>" +
            "       AND resource_type = #{permission.resourceType}" +
            "   </if>" +
            "   <if test='permission.parentId != null and permission.parentId != \"\"'>" +
            "       AND parent_id = #{permission.parentId}" +
            "   </if>" +
            "   <if test='permission.orderNo != null and permission.orderNo != \"\"'>" +
            "       AND order_no = #{permission.orderNo}" +
            "   </if>" +
            "   <if test='permission.icon != null and permission.icon != \"\"'>" +
            "       AND icon = #{permission.icon}" +
            "   </if>" +
            "</where>" +
            "LIMIT #{size} OFFSET #{page}" +
            "</script>")
    List<Permission> findByConditions(@Param("permission") Permission permission, @Param("page") Integer page, @Param("size") Integer size);
    //关联查询角色的权限列表
    @Select("select permission.* from permission " +
            "inner join role_permission on permission_id = pid " +
            "inner join role on role_id = rid " +
            "where rid = #{rid}")
    List<Permission> findPermissions(@Param("rid") Integer rid);

    @Select("SELECT COUNT(*) FROM permission")
    int selectTotalCount();

    @Update("update permission set " +
            "permission_name = #{permissionName},permission_code = #{permissionCode}," +
            "resource_url = #{resourceUrl},resource_type = #{resourceType}," +
            "parent_id = #{parentId},order_no = #{orderNo},icon = #{icon} " +
            "where pid = #{pid}")
    int updatePermission(Permission permission);

}