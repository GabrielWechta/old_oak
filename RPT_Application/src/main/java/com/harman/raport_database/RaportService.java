package com.harman.raport_database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaportService {

	@Autowired
	RaportRepository raportRepository;

	public void save(Raport raport) {
		raportRepository.save(raport);
	}

}