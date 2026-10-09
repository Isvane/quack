package com.isvane.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class DuckInventory extends PanacheEntity {

	public int storeDucks;
	public int userDucks;
}
