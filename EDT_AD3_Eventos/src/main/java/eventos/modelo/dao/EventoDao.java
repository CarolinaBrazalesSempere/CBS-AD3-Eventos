package eventos.modelo.dao;

import java.util.List;

import eventos.modelo.entitis.Evento;

public interface EventoDao {
	
	List<Evento> todos();
	Evento buscarUno(int idEvento);
	List<Evento> findByActive();
	List<Evento> findByDestacado();
	List<Evento> findByDestAct();
	List<Evento> porTipo(int idTipo);

}
