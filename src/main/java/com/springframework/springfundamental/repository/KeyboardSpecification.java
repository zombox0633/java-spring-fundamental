package com.springframework.springfundamental.repository;

import com.springframework.springfundamental.entity.Keyboard;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class KeyboardSpecification {

    public Specification<Keyboard> nameSpec(String inputName){
        //root ใช้สำหรับการดำเนินการกับ entities
        //query ที่จะดำเนินการ
        //criteriaBuilder ใช้สำหรับสร้างเงื่อนไขของ query

        //default
//        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%"+inputName+"%");

        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("name")),"%"+inputName.toLowerCase()+"%");
    }

    public Specification<Keyboard> quantitySpec(int inputQuantity){
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .greaterThanOrEqualTo(root.get("quantity"), inputQuantity);
        //greaterThanOrEqualTo มากกว่าหรือเท่ากับ
    }

    public Specification<Keyboard> priceSpec(double min, double max){
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), min, max);
    }
}
