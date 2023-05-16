package com.wisedrive.dealerapp1.pojos.pojos;

import java.util.ArrayList;
import java.util.List;

public class Feature {
    private String moduleId;
    private String partId;
    private String isPresent;

    public Feature(String moduleId, String partId, String isPresent) {
        this.moduleId = moduleId;
        this.partId = partId;
        this.isPresent = isPresent;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(String isPresent) {
        this.isPresent = isPresent;
    }
}



