package com.example.RestSecurityTaskManagementSystem.helper;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HelpForService {

    private static final List<String> ALLOWED_SORT_FOR_TASKS = List.of("id","title","description","status","priority","createdAt","updatedAt","user.id");

    public <T> T checkId(Long id, JpaRepository<T, Long> repository, String entity){
        if (id<=0) throw new IllegalArgumentException("Id is not valid");
        T result = repository.findById(id).orElseThrow(()->new EntityNotFoundException(entity+"with id {"+id+"} does not found"));
        return result;
    }

    public void checkSort(Pageable pageable){
        for (Sort.Order order : pageable.getSort()){
            if (!ALLOWED_SORT_FOR_TASKS.contains(order.getProperty())){
                throw new IllegalArgumentException("Invalid sort field");
            }
        }
    }

    public boolean isOwner(){

    }
}
