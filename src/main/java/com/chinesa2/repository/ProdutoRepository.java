package com.chinesa2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chinesa2.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}