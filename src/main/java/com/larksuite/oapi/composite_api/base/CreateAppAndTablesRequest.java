package com.larksuite.oapi.composite_api.base;

import com.lark.oapi.core.utils.Lists;
import com.lark.oapi.service.bitable.v1.model.ReqTable;

import java.util.ArrayList;
import java.util.List;

public class CreateAppAndTablesRequest {
    private String name;
    private String folderToken;
    private List<ReqTable> tables = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFolderToken() {
        return folderToken;
    }

    public void setFolderToken(String folderToken) {
        this.folderToken = folderToken;
    }

    public List<ReqTable> getTables() {
        return tables;
    }

    public void setTables(List<ReqTable> tables) {
        this.tables = tables;
    }
}
