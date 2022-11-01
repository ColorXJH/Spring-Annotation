package com.color.repository;

import org.springframework.stereotype.Repository;

/**
 * @author ColorXJH
 * @version 1.0
 * @description:
 * @date 2022/10/21 9:57
 */
@Repository
public class MyRepository {
    private String label="label1";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "MyRepository{" +
                "label='" + label + '\'' +
                '}';
    }
}
