package com.supportportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.supportportal.domain.Pickliste;

@Repository
@EnableJpaRepositories
public interface PickListeRepository extends JpaRepository<Pickliste, Long>{

	@Query(value= "SELECT *  \r\n"
			+" from pickliste \r\n"
			+ "inner join ligne_production ON pickliste.id_ligne_production=ligne_production.id_ligne_prod \r\n" 		
			+ "inner join unite_fabrication ON  unite_fabrication.id_uf = ligne_production.uf \r\n"   								
			+ "inner join status  ON  pickliste.id_status=status.id_status  \r\n"
			+"\r\n"
			+ "where  unite_fabrication.code=:uf AND status.code in ('DEMANDER','SERVI') AND pickliste.magasin=:mag \r\n"			
			+ "ORDER BY status.code" , nativeQuery=true )
	List<Pickliste> findPickListsByUfAndMagasin(String uf,  String mag);
	
	long countByStatus_Code(String statusCode);
}
