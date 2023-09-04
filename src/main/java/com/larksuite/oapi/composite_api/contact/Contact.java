package com.larksuite.oapi.composite_api.contact;

import com.lark.oapi.Client;
import com.lark.oapi.core.response.BaseResponse;
import com.lark.oapi.core.utils.Lists;
import com.lark.oapi.service.contact.v3.model.*;

import java.util.Arrays;
import java.util.List;


public class Contact {

    /**
     * 获取部门下所有用户列表，使用到两个OpenAPI：
     * 1. [获取子部门列表](<a href="https://open.feishu.cn/document/server-docs/contact-v3/department/children">...</a>)
     * 2. [获取部门直属用户列表](<a href="https://open.feishu.cn/document/server-docs/contact-v3/user/find_by_department">...</a>)
     */
    public static BaseResponse<?> ListUserByDepartment(Client client, ListUserByDepartmentRequest request) throws Exception {
        // 获取子部门列表
        ChildrenDepartmentReq childrenDepartmentReq = ChildrenDepartmentReq.newBuilder()
                .departmentIdType("open_department_id")
                .departmentId(request.getDepartmentId())
                .build();

        ChildrenDepartmentResp childrenDepartmentResp = client.contact().department().children(childrenDepartmentReq);

        if (!childrenDepartmentResp.success()) {
            System.out.printf("client.contact.department.children failed, code: %d, msg: %s, logId: %s%n",
                    childrenDepartmentResp.getCode(), childrenDepartmentResp.getMsg(), childrenDepartmentResp.getRequestId());
            return childrenDepartmentResp;
        }

        // 获取部门直属用户列表
        List<User> users = Lists.newArrayList();
        List<String> departments = Lists.newArrayList(request.getDepartmentId());
        Department[] items = childrenDepartmentResp.getData().getItems();
        for (Department item : items) {
            departments.add(item.getOpenDepartmentId());
        }
        for (String department : departments) {
            FindByDepartmentUserReq findByDepartmentUserReq = FindByDepartmentUserReq.newBuilder()
                    .departmentId(department)
                    .build();

            FindByDepartmentUserResp findByDepartmentUserResp = client.contact().user().findByDepartment(findByDepartmentUserReq);

            if (!findByDepartmentUserResp.success()) {
                System.out.printf("client.contact.user.findByDepartment failed, code: %d, msg: %s, logId: %s%n",
                        findByDepartmentUserResp.getCode(), findByDepartmentUserResp.getMsg(), findByDepartmentUserResp.getRequestId());
                return findByDepartmentUserResp;
            }

            users.addAll(Arrays.asList(findByDepartmentUserResp.getData().getItems()));
        }

        // 返回结果
        ListUserByDepartmentResponse response = new ListUserByDepartmentResponse();
        response.setCode(0);
        response.setMsg("success");
        response.setChildrenDepartmentResponse(childrenDepartmentResp.getData());
        response.setFindByDepartmentUserResponse(users);

        return response;
    }
}
