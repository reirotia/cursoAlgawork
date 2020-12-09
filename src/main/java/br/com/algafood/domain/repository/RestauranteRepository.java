package br.com.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>
, RestauranteRepositoryQueries {

	@Query("from Restaurante r join fetch r.cozinha")
	List<Restaurante> findAll();
	
	//List<Restaurante> 
	
//	@Query("from Restaurante where nome like %:nome% and cozinha.id= :id" )
//	List<Restaurante> consultaPorNome(String nome, @Param("id") Long cozinha);
	
//	List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
}
