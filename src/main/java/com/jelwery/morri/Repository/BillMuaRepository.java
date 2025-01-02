package com.jelwery.morri.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jelwery.morri.Model.BillMua;

public interface BillMuaRepository  extends MongoRepository<BillMua, String> {
}
