/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.compileflow.idea.graph.model;

/**
 * @author xuan
 * @since 2020/5/12
 */
public class UserTaskNodeModel extends BaseNodeModel {

    public static final String TIME_TYPE_ABSOLUTE = "absolute";
    public static final String TIME_TYPE_RELATIVE = "relative";
    public static final String TIME_TYPE_USER_DEFINE = "customDefine";

    public static final String GROUP_TYPE = "groupId";
    public static final String USER_TYPE = "userId";

    private String groupId;
    private String userId;
    private ActionModel userAction;
    private String userType;
    //
    private String timerType;
    private String timeExpress;
    //
    private String signType;
    private String signPercent;
    private int priority;
    private String formKey;
    //
    private ActionModel inAction;
    private ActionModel outAction;
    //
    private Long retryMax;
    private Long retryInterVal;

    private UserTaskNodeModel() {
    }

    public static UserTaskNodeModel of() {
        return new UserTaskNodeModel();
    }

    public static UserTaskNodeModel getFromCellValue(Object cellValue) {
        return (UserTaskNodeModel)cellValue;
    }

    /////////////////////// get set  ///////////////////////

    public String getTimerType() {
        return timerType;
    }

    public void setTimerType(String timerType) {
        this.timerType = timerType;
    }

    public String getTimeExpress() {
        return timeExpress;
    }

    public void setTimeExpress(String timeExpress) {
        this.timeExpress = timeExpress;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public ActionModel getUserAction() {
        return userAction;
    }

    public void setUserAction(ActionModel userAction) {
        this.userAction = userAction;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    ///////////

    public Long getRetryMax() {
        return retryMax;
    }

    public void setRetryMax(Long retryMax) {
        this.retryMax = retryMax;
    }

    public Long getRetryInterVal() {
        return retryInterVal;
    }

    public void setRetryInterVal(Long retryInterVal) {
        this.retryInterVal = retryInterVal;
    }

    public String getSignPercent() {
        return signPercent;
    }

    public void setSignPercent(String signPercent) {
        this.signPercent = signPercent;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ActionModel getInAction() {
        return inAction;
    }

    public void setInAction(ActionModel inAction) {
        this.inAction = inAction;
    }

    public ActionModel getOutAction() {
        return outAction;
    }

    public void setOutAction(ActionModel outAction) {
        this.outAction = outAction;
    }

}
