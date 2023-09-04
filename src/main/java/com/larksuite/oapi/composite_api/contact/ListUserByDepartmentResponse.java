package com.larksuite.oapi.composite_api.contact;

import com.lark.oapi.core.response.BaseResponse;
import com.lark.oapi.service.contact.v3.model.ChildrenDepartmentRespBody;
import com.lark.oapi.service.contact.v3.model.FindByDepartmentUserRespBody;
import com.lark.oapi.service.contact.v3.model.User;

import java.util.List;

public class ListUserByDepartmentResponse extends BaseResponse<Void> {
    private ChildrenDepartmentRespBody childrenDepartmentResponse;
    private List<User> findByDepartmentUserResponse;

    public ChildrenDepartmentRespBody getChildrenDepartmentResponse() {
        return childrenDepartmentResponse;
    }

    public void setChildrenDepartmentResponse(ChildrenDepartmentRespBody childrenDepartmentResponse) {
        this.childrenDepartmentResponse = childrenDepartmentResponse;
    }

    public List<User> getFindByDepartmentUserResponse() {
        return findByDepartmentUserResponse;
    }

    public void setFindByDepartmentUserResponse(List<User> findByDepartmentUserResponse) {
        this.findByDepartmentUserResponse = findByDepartmentUserResponse;
    }
}
