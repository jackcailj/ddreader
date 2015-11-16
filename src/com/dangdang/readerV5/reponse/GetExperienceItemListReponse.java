package com.dangdang.readerV5.reponse;

import com.dangdang.account.meta.AccountExperienceItems;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by cailianjie on 2015-11-16.
 */
public class GetExperienceItemListReponse {

    Integer accountExperienceTotal;
    Integer currentExperience;
    Integer currentGrade;
    Integer nextExperience;
    Integer nextGrade;
    Integer preExperience;
    Integer preGrade;

    List<AccountExperienceItems> accountExperienceList;

    public Integer getAccountExperienceTotal() {
        return accountExperienceTotal;
    }

    public void setAccountExperienceTotal(Integer accountExperienceTotal) {
        this.accountExperienceTotal = accountExperienceTotal;
    }

    public Integer getCurrentExperience() {
        return currentExperience;
    }

    public void setCurrentExperience(Integer currentExperience) {
        this.currentExperience = currentExperience;
    }

    public Integer getCurrentGrade() {
        return currentGrade;
    }

    public void setCurrentGrade(Integer currentGrade) {
        this.currentGrade = currentGrade;
    }

    public Integer getNextExperience() {
        return nextExperience;
    }

    public void setNextExperience(Integer nextExperience) {
        this.nextExperience = nextExperience;
    }

    public Integer getNextGrade() {
        return nextGrade;
    }

    public void setNextGrade(Integer nextGrade) {
        this.nextGrade = nextGrade;
    }

    public Integer getPreExperience() {
        return preExperience;
    }

    public void setPreExperience(Integer preExperience) {
        this.preExperience = preExperience;
    }

    public Integer getPreGrade() {
        return preGrade;
    }

    public void setPreGrade(Integer preGrade) {
        this.preGrade = preGrade;
    }

    public List<AccountExperienceItems> getAccountExperienceList() {
        return accountExperienceList;
    }

    public void setAccountExperienceList(List<AccountExperienceItems> accountExperienceList) {
        this.accountExperienceList = accountExperienceList;
    }
}
