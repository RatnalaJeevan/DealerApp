package com.wisedrive.dealerapp1.pojos.pojos;

public class Feature {
    private String moduleId;
    private String partId;
    private String isPresent;



    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getPartId(String partId) {
        return this.partId;
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



