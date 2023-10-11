package com.security.model;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "roles")
@AllArgsConstructor
public class Role {

    @Id
    private String roleId;
    private String roleName;
    private Integer status;


    public Role() {

    }

    public Role(String id, String name) {
        this.roleId = id;
        this.roleName = name;
    }

    public static Role createWithId(String id) {
        Role role = new Role();
        role.setRoleId(id);
        return role;
    }
}

//    public Role(String roleName) {
//        this.roleName = roleName;
//    }
//}
