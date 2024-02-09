package eventos.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eventos.modelo.entitis.Evento;

public interface EventoRepository extends JpaRepository <Evento, Integer> {
	
	@Query("select e from Evento e where e.estado = 'Activo'")
	public List<Evento> findByEstado();
	
	@Query("select e from Evento e where e.destacado = 'S'")
	public List<Evento> findByDestacado();
	
	@Query("select e from Evento e where e.estado = 'Activo' AND e.destacado = 'S'")
	public List<Evento> findByDestAct();
	
	@Query("select e from Evento e where e.tipo.idTipo = ?1")
	public List<Evento> findByTipo(int idTipo);

}
