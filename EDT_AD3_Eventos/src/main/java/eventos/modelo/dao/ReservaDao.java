package eventos.modelo.dao;

import java.util.List;

import eventos.modelo.entitis.Reserva;
import eventos.modelo.entitis.Usuario;

public interface ReservaDao {
	
	int insertOne(Reserva reserva);
	int delete(int idReserva);
	List<Reserva> reserPorUsuario(Usuario usuario);
	int eventoReservadoPorUsuario(String username, int idEvento);
	int reservasPorEvento(int idEvento);
	
	
	
}
