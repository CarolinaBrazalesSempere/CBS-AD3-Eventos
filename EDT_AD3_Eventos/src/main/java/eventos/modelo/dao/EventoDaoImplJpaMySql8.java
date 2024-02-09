package eventos.modelo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.modelo.entitis.Evento;
import eventos.modelo.repository.EventoRepository;

@Repository
public class EventoDaoImplJpaMySql8 implements EventoDao {

	@Autowired
	private EventoRepository erepo;

	@Override
	public List<Evento> todos() {
		return erepo.findAll();
	}

	@Override
	public Evento buscarUno(int idEvento) {
		
		 return erepo.findById(idEvento).orElse(null);
	}

	@Override
	public List<Evento> findByActive() {
		
		return erepo.findByEstado();
	}

	@Override
	public List<Evento> findByDestacado() {
		
		return erepo.findByDestacado();
	}

	@Override
	public List<Evento> findByDestAct() {
		
		return erepo.findByDestAct();
	}

	@Override
	public List<Evento> porTipo(int idTipo) {
		
		return erepo.findByTipo(idTipo);
	}

	

	

	

	
	
	
}
