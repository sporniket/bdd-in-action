package com.sporniket.littlecauldron.api.catalog.entities;

@org.springframework.data.repository.NoRepositoryBean
public interface FinderOfProducts extends org.springframework.data.jpa.repository.JpaRepository<EntityProducts, Integer> {
  EntityProducts findBySku(String sku);
}

